package com.parking.rest.resources;

import com.parking.JsonViews;
import com.parking.dao.post.PostDao;
import com.parking.entity.Post;
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
@Path("/post")
public class PostResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostDao postDao;

    @Autowired
    private ObjectMapper mapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String list() throws IOException {
        this.logger.info("list()");

        ObjectWriter viewWriter;
        if (this.isAdmin()) {
            viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
        } else {
            viewWriter = this.mapper.writerWithView(JsonViews.User.class);
        }
        List<Post> allEntries = this.postDao.findAll();

        return viewWriter.writeValueAsString(allEntries);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Post read(@PathParam("id") Long id) {
        this.logger.info("read(id)");

        Post post = this.postDao.find(id);
        if (post == null) {
            throw new WebApplicationException(404);
        }
        return post;
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Post create(Post post) {
        this.logger.info("create(): " + post);

        return this.postDao.save(post);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Post update(@PathParam("id") Long id, Post post) {
        this.logger.info("update(): " + post);

        return this.postDao.save(post);
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        this.logger.info("delete(id)");

        this.postDao.delete(id);
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





    /*
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedIn = accountService.findByAccountName(details.getUsername());
            if (loggedIn.getId() == accountId) {
                try {
                    AccountGroup createdAccountGroup = accountService.createAccountGroup(accountId, res.toAccountGroup());
                    AccountGroupResource accountGroupResource = new AccountGroupResourceAsm().toResource(createdAccountGroup);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(URI.create(accountGroupResource.getLink("self").getHref()));
                    return new ResponseEntity<AccountGroupResource>(accountGroupResource, headers, HttpStatus.CREATED);
                } catch (AccountDoesNotExistException exception) {
                    throw new NotFoundException(exception);
                } catch (BlogExistsException exception) {
                    throw new ConflictException(exception);
                }
            } else {
                throw new ForbiddenException();
            }
        } else {
            throw new ForbiddenException();
        }

     */
}