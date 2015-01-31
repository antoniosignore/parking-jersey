package com.parking.rest.resources;

import com.jayway.jaxrs.hateoas.Linkable;
import com.jayway.jaxrs.hateoas.core.HateoasResponse;
import com.jayway.jaxrs.hateoas.support.AtomRels;
import com.parking.entity.Account;
import com.parking.entity.Vehicle;
import com.parking.rest.exceptions.ForbiddenException;
import com.parking.rest.hateoas.VehicleDto;
import com.parking.services.AccountService;
import com.parking.services.VehicleService;
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
@Path("/vehicles")
public class VehicleResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private VehicleService vehicleService;

    @GET
    @Linkable(LinkableIds.VEHICLES_LIST_ID)
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedAccount = accountService.findByUserName(details.getUsername());
            try {
                List<Vehicle> allEntries = this.vehicleService.findAllVehicleByAccount(loggedAccount);
                return HateoasResponse
                        .ok(VehicleDto.fromBeanCollection(allEntries))
                        .selfLink(LinkableIds.VEHICLE_NEW_ID)
                        .selfEach(LinkableIds.VEHICLE_DETAILS_ID, "id").build();
            } catch (AccountDoesNotExistException exception) {
                throw new NotFoundException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @GET
    @Linkable(LinkableIds.VEHICLE_DETAILS_ID)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getVehicleById(@PathParam("id") Long id) {
        Vehicle vehicle = this.vehicleService.findVehicle(id);
        HateoasResponse.HateoasResponseBuilder builder =
                HateoasResponse
                        .ok(VehicleDto.fromBean(vehicle))
                        .link(LinkableIds.VEHICLE_UPDATE_ID, AtomRels.SELF, id);
        return builder.build();
    }

    @POST
    @Linkable(value = LinkableIds.VEHICLE_NEW_ID, templateClass = VehicleDto.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newVehicle(VehicleDto vehicle) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedAccount = accountService.findByUserName(details.getUsername());
            try {
                Vehicle createVehicle = vehicleService.createVehicle(loggedAccount, vehicle.toBean(vehicle));
                return HateoasResponse
                        .created(LinkableIds.VEHICLE_DETAILS_ID, createVehicle.getId())
                        .entity(VehicleDto.fromBean(createVehicle)).build();
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
    @Path("{id}")
    public Vehicle update(@PathParam("id") Long id, Vehicle vehicle) {
        return vehicleService.save(id, vehicle);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        this.vehicleService.delete(id);
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