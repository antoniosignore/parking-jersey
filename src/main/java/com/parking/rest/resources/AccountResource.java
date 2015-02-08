package com.parking.rest.resources;

import com.jayway.jaxrs.hateoas.Linkable;
import com.jayway.jaxrs.hateoas.core.HateoasResponse;
import com.jayway.jaxrs.hateoas.support.AtomRels;
import com.parking.entity.Account;
import com.parking.rest.TokenUtils;
import com.parking.rest.dto.*;
import com.parking.rest.exceptions.ForbiddenException;
import com.parking.services.*;
import com.parking.services.exceptions.AccountDoesNotExistException;
import com.parking.transfer.TokenTransfer;
import com.parking.transfer.UserTransfer;
import com.sun.jersey.api.NotFoundException;
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
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Component
@Path("/accounts")
public class AccountResource {

    @Autowired
    ConnectionService connectionService;
    @Autowired
    ParkingService parkingService;
    @Autowired
    BlogEntryService blogEntryService;
    @Autowired
    AccountGroupService accountGroupService;
    @Autowired
    VehicleService vehicleService;
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

    @GET
    @Path("/{id}/connections")
    @Produces("application/json")
    @Linkable(LinkableIds.ACCOUNT_CONNECTIONS_ID)
    public Response getAccountConnections(@PathParam("id") Long id) {

        try {
            Account user = accountService.findAccount(id);

            Collection<ConnectionDto> connections = ConnectionDto.fromBeanCollection(
                    connectionService.findAllConnectionByAccount(user));

            return HateoasResponse.ok(connections)
                    .selfEach(LinkableIds.CONNECTION_DETAILS_ID, "id").build();

        } catch (AccountDoesNotExistException exception) {
            throw new NotFoundException();
        }
    }


    @GET
    @Path("/{id}/parkings")
    @Produces("application/json")
    @Linkable(LinkableIds.ACCOUNT_PARKINGS_ID)
    public Response getAccountParkings(@PathParam("id") Long id) {

        try {
            Account user = accountService.findAccount(id);

            Collection<ParkingDto> parkings = ParkingDto.fromBeanCollection(
                    parkingService.findAllParkingByAccount(user));

            return HateoasResponse.ok(parkings)
                    .selfEach(LinkableIds.PARKING_DETAILS_ID, "id").build();

        } catch (AccountDoesNotExistException exception) {
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/{id}/blog-entries")
    @Produces("application/json")
    @Linkable(LinkableIds.ACCOUNT_POSTS_ID)
    public Response getAccountBlogEntries(@PathParam("id") Long id) {

        try {
            Account user = accountService.findAccount(id);

            Collection<BlogEntryDto> posts = BlogEntryDto.fromBeanCollection(
                    blogEntryService.findAllBlogEntries(user));

            return HateoasResponse.ok(posts)
                    .selfEach(LinkableIds.BLOG_ENTRY_DETAILS_ID, "id").build();

        } catch (AccountDoesNotExistException exception) {
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/{id}/account-groups")
    @Produces("application/json")
    @Linkable(LinkableIds.ACCOUNT_GROUPS_ID)
    public Response getAccountGroups(@PathParam("id") Long id) {

        try {
            Account user = accountService.findAccount(id);

            Collection<AccountGroupDto> groups = AccountGroupDto.fromBeanCollection(
                    accountGroupService.findAllAccountGroupByAccount(user));
            return HateoasResponse.ok(groups)
                    .selfEach(LinkableIds.ACCOUNT_GROUP_DETAILS_ID, "id").build();

        } catch (AccountDoesNotExistException exception) {
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/{id}/vehicles")
    @Produces("application/json")
    @Linkable(LinkableIds.ACCOUNT_VEHICLES_ID)
    public Response getAccountVehicles(@PathParam("id") Long id) {

        try {
            Account user = accountService.findAccount(id);

            Collection<VehicleDto> vehicles = VehicleDto.fromBeanCollection(
                    vehicleService.findAllVehicleByAccount(user));

            return HateoasResponse.ok(vehicles)
                    .selfEach(LinkableIds.VEHICLE_DETAILS_ID, "id").build();

        } catch (AccountDoesNotExistException exception) {
            throw new NotFoundException();
        }
    }


    @POST
    @Linkable(value = LinkableIds.ACCOUNT_NEW_ID, templateClass = AccountDto.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newAccount(AccountDto account) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedAccount = accountService.findByName(details.getUsername());
            try {
                Account createAccount = accountService.createAccount(account.toBean(account));
                return HateoasResponse
                        .created(LinkableIds.ACCOUNT_DETAILS_ID, createAccount.getId())
                        .entity(AccountDto.fromBean(createAccount)).build();
            } catch (AccountDoesNotExistException exception) {
                throw new NotFoundException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @PUT
    @Linkable(value = LinkableIds.ACCOUNT_UPDATE_ID, templateClass = AccountDto.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, AccountDto dto) {
        Account user = this.accountService.findAccount(id);
        if (user == null) return Response.status(Response.Status.NOT_FOUND).build();

        user.setName(dto.getName());
        user.setLongitude(dto.getLongitude());
        user.setLatitude(dto.getLatitude());
        user.setPassword(dto.getPassword());
        user.setPassword(dto.getPassword());
        Account saved;
        try {
            saved = accountService.update(user);
        } catch (Exception e) {
            throw new ForbiddenException();
        }

        HateoasResponse.HateoasResponseBuilder builder =
                HateoasResponse
                        .ok(AccountDto.fromBean(saved))
                        .link(LinkableIds.ACCOUNT_UPDATE_ID, AtomRels.SELF, id);
        return builder.build();
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        Account veh = this.accountService.findAccount(id);
        if (veh == null) throw new NotFoundException();
        this.accountService.delete(veh.getId());
    }

}