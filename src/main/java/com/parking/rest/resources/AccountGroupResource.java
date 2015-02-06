package com.parking.rest.resources;

import com.jayway.jaxrs.hateoas.Linkable;
import com.jayway.jaxrs.hateoas.core.HateoasResponse;
import com.jayway.jaxrs.hateoas.support.AtomRels;
import com.parking.entity.Account;
import com.parking.entity.AccountGroup;
import com.parking.rest.exceptions.ForbiddenException;
import com.parking.rest.hateoas.AccountGroupDto;
import com.parking.services.AccountGroupService;
import com.parking.services.AccountService;
import com.parking.services.exceptions.AccountDoesNotExistException;
import com.sun.jersey.api.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/accountGroups")
public class AccountGroupResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountGroupService accountGroupService;

    @GET
    @Linkable(LinkableIds.ACCOUNTS_GROUP_LIST_ID)
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedAccount = accountService.findByUserName(details.getUsername());
            try {
                List<AccountGroup> allEntries = this.accountGroupService.findAllAccountGroupByAccount(loggedAccount);
                return HateoasResponse
                        .ok(AccountGroupDto.fromBeanCollection(allEntries))
                        .selfLink(LinkableIds.ACCOUNT_GROUP_NEW_ID)
                        .selfEach(LinkableIds.ACCOUNT_GROUP_DETAILS_ID, "id").build();
            } catch (AccountDoesNotExistException exception) {
                throw new NotFoundException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @GET
    @Linkable(LinkableIds.ACCOUNT_GROUP_DETAILS_ID)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getAccountGroupById(@PathParam("id") Long id) {

        AccountGroup accountgroup = this.accountGroupService.findAccountGroup(id);
        if (accountgroup == null) return Response.status(Response.Status.NOT_FOUND).build();

        HateoasResponse.HateoasResponseBuilder builder =
                HateoasResponse
                        .ok(AccountGroupDto.fromBean(accountgroup))
                        .link(LinkableIds.ACCOUNT_GROUP_DETAILS_ID, AtomRels.SELF, id);
        return builder.build();
    }

    @POST
    @Linkable(value = LinkableIds.ACCOUNT_GROUP_NEW_ID, templateClass = AccountGroupDto.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newAccountGroup(AccountGroupDto accountgroup) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedAccount = accountService.findByUserName(details.getUsername());
            try {
                AccountGroup createAccountgroup = accountGroupService.createAccountGroup(loggedAccount, accountgroup.toBean(accountgroup));
                return HateoasResponse
                        .created(LinkableIds.ACCOUNT_GROUP_DETAILS_ID, createAccountgroup.getId())
                        .entity(AccountGroupDto.fromBean(createAccountgroup)).build();
            } catch (AccountDoesNotExistException exception) {
                throw new NotFoundException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @POST
    @Linkable(value = LinkableIds.ACCOUNT_GROUP_UPDATE_ID, templateClass = AccountGroupDto.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, AccountGroup accountgroup) {

        AccountGroup group = this.accountGroupService.findAccountGroup(id);
        if (group == null) return Response.status(Response.Status.NOT_FOUND).build();

        AccountGroup saved;
        try {
            saved = accountGroupService.update(accountgroup);
        } catch (Exception e) {
            throw new ForbiddenException();
        }

        HateoasResponse.HateoasResponseBuilder builder =
                HateoasResponse
                        .ok(AccountGroupDto.fromBean(saved))
                        .link(LinkableIds.ACCOUNT_GROUP_UPDATE_ID, AtomRels.SELF, id);
        return builder.build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        AccountGroup group = this.accountGroupService.findAccountGroup(id);
        if (group == null) throw new NotFoundException();
        this.accountGroupService.delete(id);
    }


    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof String && (principal).equals("anonymousUser")) {
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            if (authority.toString().equals("admin")) {
                return true;
            }
        }
        return false;
    }
}