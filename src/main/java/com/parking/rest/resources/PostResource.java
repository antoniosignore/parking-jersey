package com.parking.rest.resources;

import com.jayway.jaxrs.hateoas.Linkable;
import com.jayway.jaxrs.hateoas.core.HateoasResponse;
import com.jayway.jaxrs.hateoas.support.AtomRels;
import com.parking.dao.post.PostDao;
import com.parking.entity.Post;
import com.parking.rest.hateoas.PostDto;
import com.parking.services.AccountService;
import org.codehaus.jackson.map.ObjectMapper;
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
@Path("/post")
public class PostResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostDao postDao;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AccountService accountService;

    @GET
    @Linkable(LinkableIds.POSTS_LIST_ID)
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        List<Post> allEntries = this.postDao.findAll();
        return HateoasResponse
                .ok(PostDto.fromBeanCollection(allEntries))
                .selfLink(LinkableIds.POST_NEW_ID)
                .selfEach(LinkableIds.POST_DETAILS_ID, "id").build();
    }


//    public String list() throws IOException {
//        this.logger.info("list()");
//
//        ObjectWriter viewWriter;
//        if (this.isAdmin()) {
//            viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
//        } else {
//            viewWriter = this.mapper.writerWithView(JsonViews.User.class);
//        }
//
//        List<Post> allEntries = this.postDao.findAll();
//
//        return viewWriter.writeValueAsString(allEntries);
//    }

    @GET
    @Linkable(LinkableIds.POST_DETAILS_ID)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getPostById(@PathParam("id") Long id) {
        Post post = this.postDao.find(id);
        HateoasResponse.HateoasResponseBuilder builder = HateoasResponse
                .ok(PostDto.fromBean(post))
                .link(LinkableIds.POST_UPDATE_ID, AtomRels.SELF, id);
        return builder.build();
    }


//    @GET
//    @Linkable(LinkableIds.BOOK_DETAILS_ID)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getBookById(@PathParam("id") Integer id) {
//        Book book = bookRepository.getBookById(id);
//        HateoasResponse.HateoasResponseBuilder builder = HateoasResponse
//                .ok(BookDto.fromBean(book))
//                .link(LinkableIds.BOOK_UPDATE_ID, AtomRels.SELF, id);
//
//        if (!book.isBorrowed()) {
//            builder.link(LinkableIds.LOAN_NEW_ID, Rels.LOANS);
//        } else {
//            builder.link(LinkableIds.LOAN_DETAILS_ID, Rels.LOAN, book.getId());
//        }
//        return builder.build();
//    }

//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Post create(Post post) {
//        this.logger.info("create(): " + post);
//
//        return this.postDao.save(post);
//    }

//    @POST
//    @Linkable(value = LinkableIds.POST_NEW_ID, templateClass = PostDto.class)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response newPost(PostDto book) {
//
//        Post newBook = bookRepository
//                .newBook(book.getAuthor(), book.getTitle());
//
//        return HateoasResponse
//                .created(LinkableIds.POST_DETAILS_ID, newBook.getId())
//                .entity(PostDto.fromBean(newBook)).build();
//    }


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