package com.parking.rest.resources;

import com.parking.rest.TokenUtils;
import com.parking.services.AccountService;
import com.parking.transfer.TokenTransfer;
import com.parking.transfer.UserTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;


@Component
@Path("/accounts")
public class AccountResource {

    @Autowired
    private UserDetailsService userService;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authManager;

    @Autowired
    private AccountService accountService;

    /**
     * Retrieves the currently logged in account.
     *
     * @return A transfer containing the username and the roles.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserTransfer getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
            throw new WebApplicationException(401);
        }
        UserDetails userDetails = (UserDetails) principal;

        return new UserTransfer(userDetails.getUsername(), this.createRoleMap(userDetails));
    }

    /**
     * Authenticates a account and creates an authentication token.
     *
     * @param username The name of the account.
     * @param password The password of the account.
     * @return A transfer containing the authentication token.
     */
    @Path("authenticate")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public TokenTransfer authenticate(
            @FormParam("username") String username,
            @FormParam("password") String password) {

        System.out.println("\n\n************\nusername = " + username);
        System.out.println("password = " + password);
        System.out.println("authManager = " + authManager);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = this.authManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

		/*
         * Reload account as password of authentication principal will be null after authorization and
		 * password is needed for token generation
		 */
        UserDetails userDetails = this.userService.loadUserByUsername(username);

        return new TokenTransfer(TokenUtils.createToken(userDetails));
    }

    private Map<String, Boolean> createRoleMap(UserDetails userDetails) {
        Map<String, Boolean> roles = new HashMap<>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.put(authority.getAuthority(), Boolean.TRUE);
        }
        return roles;
    }

//    @Path("/{accountName}/post")
//    @Linkable(value = LinkableIds.POST_NEW_ID, templateClass = PostDto.class)
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response createPost(
//            Post post,
//            @PathParam("accountName") String accountName) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            UserDetails details = (UserDetails) principal;
//            Account loggedIn = accountService.findByUserName(details.getUsername());
//            if (loggedIn.getName().equals(accountName)) {
//                try {
//                    Post createPost = accountService.createPost(loggedIn.getId(), post);
//                    return HateoasResponse
//                            .created(LinkableIds.POST_DETAILS_ID, createPost.getId())
//                            .entity(PostDto.fromBean(createPost)).build();
//                } catch (AccountDoesNotExistException exception) {
//                    throw new NotFoundException();
//                } catch (BlogExistsException exception) {
//                    throw new ConflictException();
//                }
//            } else {
//                throw new ForbiddenException();
//            }
//        } else {
//            throw new ForbiddenException();
//        }
//    }

//    @Path("/{accountName}/post")
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Post createPost(
//            Post post,
//            @PathParam("accountName") String accountName) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            UserDetails details = (UserDetails) principal;
//            Account loggedIn = accountService.findByUserName(details.getUsername());
//            if (loggedIn.getName().equals(accountName)) {
//                try {
//
//                    System.out.println("\n\n*************\nloggedIn = " + loggedIn.getId());
//
//                    Post createPost = accountService.createPost(loggedIn.getId(), post);
//                    return createPost;
//                } catch (AccountDoesNotExistException exception) {
//                    throw new NotFoundException();
//                } catch (BlogExistsException exception) {
//                    throw new ConflictException();
//                }
//            } else {
//                throw new ForbiddenException();
//            }
//        } else {
//            throw new ForbiddenException();
//        }
//    }

//    @RequestMapping(value = "/{accountId}/blogs", method = RequestMethod.GET)
//    @PreAuthorize("permitAll")
//    public ResponseEntity<BlogListResource> findAllBlogs(
//            @PathVariable Long accountId) {
//        try {
//            BlogList blogList = accountService.findBlogsByAccount(accountId);
//            BlogListResource blogListRes = new BlogListResourceAsm().toResource(blogList);
//            return new ResponseEntity<BlogListResource>(blogListRes, HttpStatus.OK);
//        } catch (AccountDoesNotExistException exception) {
//            throw new NotFoundException(exception);
//        }
//    }


}