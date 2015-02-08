package com.parking.rest.resources;

import com.jayway.jaxrs.hateoas.Linkable;
import com.jayway.jaxrs.hateoas.core.HateoasResponse;
import com.jayway.jaxrs.hateoas.support.AtomRels;
import com.parking.entity.Account;
import com.parking.entity.AccountGroup;
import com.parking.entity.Connection;
import com.parking.rest.dto.ConnectionDto;
import com.parking.rest.exceptions.ForbiddenException;
import com.parking.services.AccountGroupService;
import com.parking.services.AccountService;
import com.parking.services.ConnectionService;
import com.parking.services.exceptions.AccountDoesNotExistException;
import com.sun.jersey.api.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/connections")
public class ConnectionResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountGroupService accountGroupService;

    @Autowired
    private ConnectionService connectionService;

    @GET
    @Linkable(LinkableIds.CONNECTIONS_LIST_ID)
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedAccount = accountService.findByName(details.getUsername());
            try {
                List<Connection> allEntries = this.connectionService.findAllConnectionByAccount(loggedAccount);
                return HateoasResponse
                        .ok(ConnectionDto.fromBeanCollection(allEntries))
                        .selfLink(LinkableIds.CONNECTION_NEW_ID)
                        .selfEach(LinkableIds.CONNECTION_DETAILS_ID, "id").build();
            } catch (AccountDoesNotExistException exception) {
                throw new NotFoundException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @GET
    @Linkable(LinkableIds.CONNECTION_DETAILS_ID)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getConnectionById(@PathParam("id") Long id) {

        Connection connection = this.connectionService.findConnection(id);
        if (connection == null) return Response.status(Response.Status.NOT_FOUND).build();

        HateoasResponse.HateoasResponseBuilder builder =
                HateoasResponse
                        .ok(ConnectionDto.fromBean(connection))
                        .link(LinkableIds.CONNECTION_DETAILS_ID, AtomRels.SELF, id);
        return builder.build();
    }

    @POST
    @Linkable(value = LinkableIds.CONNECTION_NEW_ID, templateClass = ConnectionDto.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newConnection(ConnectionDto dto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedAccount = accountService.findByName(details.getUsername());
            try {

                AccountGroup initiatorGroup = accountGroupService.findAccountGroup(dto.getInitiatorGroupId());
                Account receiver = accountService.findAccount(dto.getReceiverId());

                Connection createConnection = connectionService.createConnection(
                        loggedAccount, initiatorGroup, receiver,
                        dto.toBean(dto));

                return HateoasResponse
                        .created(LinkableIds.CONNECTION_DETAILS_ID, createConnection.getId())
                        .selfLink(LinkableIds.CONNECTION_DETAILS_ID, createConnection.getId())
                        .link(LinkableIds.ACCOUNT_GROUP_DETAILS_ID, Rels.ACCOUNT_GROUP, initiatorGroup.getId())
                        .link(LinkableIds.ACCOUNT_DETAILS_ID, Rels.ACCOUNT, initiatorGroup.getId())
                        .entity(ConnectionDto.fromBean(createConnection)).build();
            } catch (AccountDoesNotExistException exception) {
                throw new NotFoundException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @PUT
    @Linkable(value = LinkableIds.CONNECTION_UPDATE_ID, templateClass = ConnectionDto.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ConnectionDto dto) {

        Connection connection = this.connectionService.findConnection(id);
        if (connection == null) return Response.status(Response.Status.NOT_FOUND).build();
        connection.setConfirmed(dto.getConfirmed());

        Account initiator = accountService.findAccount(dto.getInitiatorId());
        AccountGroup initiatorGroup = accountGroupService.findAccountGroup(dto.getInitiatorGroupId());
        Account receiver = accountService.findAccount(dto.getReceiverId());
        AccountGroup receiverGroup = accountGroupService.findAccountGroup(dto.getReceiverGroupId());

        Connection saved;
        try {
            saved = connectionService.update(receiver, receiverGroup, initiator, initiatorGroup, connection);
        } catch (Exception e) {
            throw new ForbiddenException();
        }

        HateoasResponse.HateoasResponseBuilder builder =
                HateoasResponse
                        .ok(ConnectionDto.fromBean(saved))
                        .link(LinkableIds.ACCOUNT_GROUP_DETAILS_ID, Rels.ACCOUNT_GROUP, initiatorGroup.getId())
                        .link(LinkableIds.ACCOUNT_DETAILS_ID, Rels.ACCOUNT, initiatorGroup.getId())
                        .link(LinkableIds.ACCOUNT_DETAILS_ID, Rels.ACCOUNT, receiverGroup.getId())
                        .link(LinkableIds.CONNECTION_UPDATE_ID, AtomRels.SELF, id);
        return builder.build();
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        Connection connection = this.connectionService.findConnection(id);
        if (connection == null) throw new NotFoundException();
        this.connectionService.delete(connection.getId());
    }

}