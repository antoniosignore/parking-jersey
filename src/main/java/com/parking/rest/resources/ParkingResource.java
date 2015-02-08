package com.parking.rest.resources;

import com.jayway.jaxrs.hateoas.Linkable;
import com.jayway.jaxrs.hateoas.core.HateoasResponse;
import com.jayway.jaxrs.hateoas.support.AtomRels;
import com.parking.entity.Account;
import com.parking.entity.Parking;
import com.parking.entity.Vehicle;
import com.parking.rest.dto.ParkingDto;
import com.parking.rest.exceptions.ForbiddenException;
import com.parking.services.AccountService;
import com.parking.services.ParkingService;
import com.parking.services.VehicleService;
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
@Path("/parkings")
public class ParkingResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private VehicleService vehicleService;

    @GET
    @Linkable(LinkableIds.PARKINGS_LIST_ID)
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedAccount = accountService.findByName(details.getUsername());
            try {
                List<Parking> allEntries = this.parkingService.findAllParkingByAccount(loggedAccount);
                return HateoasResponse
                        .ok(ParkingDto.fromBeanCollection(allEntries))
                        .selfLink(LinkableIds.PARKING_NEW_ID)
                        .selfEach(LinkableIds.PARKING_DETAILS_ID, "id").build();
            } catch (AccountDoesNotExistException exception) {
                throw new NotFoundException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @GET
    @Linkable(LinkableIds.PARKING_DETAILS_ID)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getParkingById(@PathParam("id") Long id) {
        Parking parking = this.parkingService.findParking(id);
        if (parking == null) return Response.status(Response.Status.NOT_FOUND).build();

        HateoasResponse.HateoasResponseBuilder builder =
                HateoasResponse
                        .ok(ParkingDto.fromBean(parking))
                        .link(LinkableIds.VEHICLE_DETAILS_ID, Rels.VEHICLE, parking.getVehicle().getId())
                        .link(LinkableIds.PARKING_DETAILS_ID, AtomRels.SELF, id);
        return builder.build();
    }

    @POST
    @Linkable(value = LinkableIds.PARKING_NEW_ID, templateClass = ParkingDto.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newParking(ParkingDto parkingDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedAccount = accountService.findByName(details.getUsername());
            try {

                Vehicle vehicle = vehicleService.findVehicle(parkingDto.getVehicleId());
                Parking createParking = parkingService.createParking(
                        loggedAccount, vehicle, parkingDto.toBean(parkingDto));
                return HateoasResponse
                        .created(LinkableIds.PARKING_DETAILS_ID, createParking.getId())
                        .entity(parkingDto)
                        .selfLink(LinkableIds.PARKING_DETAILS_ID, createParking.getId())
                        .link(LinkableIds.VEHICLE_DETAILS_ID, Rels.VEHICLE, vehicle.getId())
                        .build();
            } catch (AccountDoesNotExistException exception) {
                throw new NotFoundException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @PUT
    @Linkable(value = LinkableIds.PARKING_UPDATE_ID, templateClass = ParkingDto.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ParkingDto dto) {

        Parking parking = this.parkingService.findParking(id);
        if (parking == null) return Response.status(Response.Status.NOT_FOUND).build();

        Vehicle vehicle = vehicleService.findVehicle(dto.getVehicleId());
        Account pickedBy = accountService.findAccount(dto.getPickedById());

        parking.setLatitude(dto.getLatitude());
        parking.setLongitude(dto.getLongitude());
        parking.setStatus(dto.getStatus());
        parking.setWhenPicked(dto.getWhenPicked());
        parking.setPickedBy(pickedBy);
        parking.setVehicle(vehicle);
        try {
            parking = parkingService.update(parking);
        } catch (Exception e) {
            throw new ForbiddenException();
        }

        HateoasResponse.HateoasResponseBuilder builder =
                HateoasResponse
                        .ok(ParkingDto.fromBean(parking))
                        .link(LinkableIds.VEHICLE_DETAILS_ID, Rels.VEHICLE, parking.getVehicle().getId())
                        .link(LinkableIds.PARKING_UPDATE_ID, AtomRels.SELF, id);
        return builder.build();
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        Parking veh = this.parkingService.findParking(id);
        if (veh == null) throw new NotFoundException();
        this.parkingService.delete(veh.getId());
    }

}