package com.parking.rest.hateoas.asm;

import com.parking.core.models.entities.Parking;
import com.rest.mvc.AccountController;
import com.rest.mvc.ParkingController;
import com.rest.resources.ParkingResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class ParkingResourceAsm extends ResourceAssemblerSupport<Parking, ParkingResource> {
    public ParkingResourceAsm() {
        super(ParkingController.class, ParkingResource.class);
    }

    @Override
    public ParkingResource toResource(Parking parking) {
        ParkingResource resource = new ParkingResource();

        resource.setRid(parking.getId());
        resource.setLatitude(parking.getLatitude());
        resource.setLongitude(parking.getLongitude());
        resource.setStatus(parking.getStatus());
        resource.setWhenPicked(parking.getWhenPicked());

        resource.add(linkTo(ParkingController.class).slash(parking.getId()).withSelfRel());
        resource.add(linkTo(ParkingController.class).slash(parking.getId()).slash("parkings").withRel("entries"));

        if (parking.getAccount() != null) resource.add(linkTo(AccountController.class).slash(parking.getAccount().getId()).withRel("owner"));
        return resource;
    }
}
