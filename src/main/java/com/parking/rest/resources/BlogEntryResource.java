package com.parking.rest.resources;

import com.jayway.jaxrs.hateoas.Linkable;
import com.jayway.jaxrs.hateoas.core.HateoasResponse;
import com.jayway.jaxrs.hateoas.support.AtomRels;
import com.parking.entity.Account;
import com.parking.entity.Post;
import com.parking.rest.exceptions.ForbiddenException;
import com.parking.rest.hateoas.PostDto;
import com.parking.services.AccountService;
import com.parking.services.PostService;
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
@Path("/blog-entries")
public class BlogEntryResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;

    @GET
    @Linkable(LinkableIds.POSTS_LIST_ID)
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedAccount = accountService.findByUserName(details.getUsername());
            try {
                List<Post> allEntries = this.postService.findAllPosts(loggedAccount);
                return HateoasResponse
                        .ok(PostDto.fromBeanCollection(allEntries))
                        .selfLink(LinkableIds.POST_NEW_ID)
                        .selfEach(LinkableIds.POST_DETAILS_ID, "id").build();
            } catch (AccountDoesNotExistException exception) {
                throw new NotFoundException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @GET
    @Linkable(LinkableIds.POST_DETAILS_ID)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getPostById(@PathParam("id") Long id) {
        Post post = this.postService.findPost(id);
        if (post == null) return Response.status(Response.Status.NOT_FOUND).build();

        HateoasResponse.HateoasResponseBuilder builder =
                HateoasResponse
                        .ok(PostDto.fromBean(post))
                        .link(LinkableIds.POST_DETAILS_ID, AtomRels.SELF, id);
        return builder.build();
    }

    @POST
    @Linkable(value = LinkableIds.POST_NEW_ID, templateClass = PostDto.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newPost(PostDto post) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedAccount = accountService.findByUserName(details.getUsername());
            try {
                Post createPost = postService.createPost(loggedAccount, post.toBean(post));
                return HateoasResponse
                        .created(LinkableIds.POST_DETAILS_ID, createPost.getId())
                        .entity(PostDto.fromBean(createPost)).build();
            } catch (AccountDoesNotExistException exception) {
                throw new NotFoundException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Post update(@PathParam("id") Long id, Post post) {
        return postService.save(id, post);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        this.postService.delete(id);
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