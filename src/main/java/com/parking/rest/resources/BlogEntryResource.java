package com.parking.rest.resources;

import com.jayway.jaxrs.hateoas.Linkable;
import com.jayway.jaxrs.hateoas.core.HateoasResponse;
import com.jayway.jaxrs.hateoas.support.AtomRels;
import com.parking.entity.Account;
import com.parking.entity.BlogEntry;
import com.parking.rest.dto.BlogEntryDto;
import com.parking.rest.exceptions.ForbiddenException;
import com.parking.services.AccountService;
import com.parking.services.BlogEntryService;
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
@Path("/blog-entries")
public class BlogEntryResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private BlogEntryService blogEntryService;

    @GET
    @Linkable(LinkableIds.BLOG_ENTRIES_LIST_ID)
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedAccount = accountService.findByName(details.getUsername());
            try {
                List<BlogEntry> allEntries = this.blogEntryService.findAllBlogEntries(loggedAccount);
                return HateoasResponse
                        .ok(BlogEntryDto.fromBeanCollection(allEntries))
                        .selfLink(LinkableIds.BLOG_ENTRY_NEW_ID)
                        .selfEach(LinkableIds.BLOG_ENTRY_DETAILS_ID, "id").build();
            } catch (AccountDoesNotExistException exception) {
                throw new NotFoundException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @GET
    @Linkable(LinkableIds.BLOG_ENTRY_DETAILS_ID)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getPostById(@PathParam("id") Long id) {
        BlogEntry blogEntry = this.blogEntryService.findBlogEntry(id);
        if (blogEntry == null) return Response.status(Response.Status.NOT_FOUND).build();

        HateoasResponse.HateoasResponseBuilder builder =
                HateoasResponse
                        .ok(BlogEntryDto.fromBean(blogEntry))
                        .link(LinkableIds.BLOG_ENTRY_DETAILS_ID, AtomRels.SELF, id);
        return builder.build();
    }

    @POST
    @Linkable(value = LinkableIds.BLOG_ENTRY_NEW_ID, templateClass = BlogEntryDto.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newPost(BlogEntryDto post) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedAccount = accountService.findByName(details.getUsername());
            try {
                BlogEntry createBlogEntry = blogEntryService.createBlogEntry(loggedAccount, post.toBean(post));
                return HateoasResponse
                        .created(LinkableIds.BLOG_ENTRY_DETAILS_ID, createBlogEntry.getId())
                        .entity(BlogEntryDto.fromBean(createBlogEntry)).build();
            } catch (AccountDoesNotExistException exception) {
                throw new NotFoundException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @PUT
    @Linkable(value = LinkableIds.BLOG_ENTRY_UPDATE_ID, templateClass = BlogEntryDto.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, BlogEntryDto dto) {
        BlogEntry veh = this.blogEntryService.findBlogEntry(id);
        if (veh == null) return Response.status(Response.Status.NOT_FOUND).build();

        veh.setTitle(dto.getTitle());
        veh.setContent(dto.getContent());

        BlogEntry saved;
        try {
            saved = blogEntryService.update(veh);
        } catch (Exception e) {
            throw new ForbiddenException();
        }

        HateoasResponse.HateoasResponseBuilder builder =
                HateoasResponse
                        .ok(BlogEntryDto.fromBean(saved))
                        .link(LinkableIds.BLOG_ENTRY_UPDATE_ID, AtomRels.SELF, id);
        return builder.build();
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        BlogEntry veh = this.blogEntryService.findBlogEntry(id);
        if (veh == null) throw new NotFoundException();
        this.blogEntryService.delete(veh.getId());
    }

}