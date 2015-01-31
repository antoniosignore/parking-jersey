package com.parking.rest.resources;

import com.parking.JsonViews;
import com.parking.dao.accountGroup.UserGroupDao;
import com.parking.entity.AccountGroup;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
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
import java.io.IOException;
import java.util.List;


@Component
@Path("/accountGroups")
public class AccountGroupResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserGroupDao userGroupDao;

    @Autowired
    private ObjectMapper mapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String list() throws JsonGenerationException, JsonMappingException, IOException {
        this.logger.info("list()");

        ObjectWriter viewWriter;
        if (this.isAdmin()) {
            viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
        } else {
            viewWriter = this.mapper.writerWithView(JsonViews.User.class);
        }
        List<AccountGroup> allEntries = this.userGroupDao.findAll();

        return viewWriter.writeValueAsString(allEntries);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public AccountGroup read(@PathParam("id") Long id) {
        this.logger.info("read(id)");

        AccountGroup accountgroup = this.userGroupDao.find(id);
        if (accountgroup == null) {
            throw new WebApplicationException(404);
        }
        return accountgroup;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public AccountGroup create(AccountGroup accountgroup) {
        this.logger.info("create(): " + accountgroup);

        return this.userGroupDao.save(accountgroup);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public AccountGroup update(@PathParam("id") Long id, AccountGroup accountgroup) {
        this.logger.info("update(): " + accountgroup);

        return this.userGroupDao.save(accountgroup);
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        this.logger.info("delete(id)");

        this.userGroupDao.delete(id);
    }


    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
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