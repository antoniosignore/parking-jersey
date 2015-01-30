package com.parking.rest.hateoas.asm;

import com.parking.core.models.entities.Vehicle;
import com.rest.mvc.AccountController;
import com.rest.mvc.VehicleController;
import com.rest.resources.VehicleResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class VehicleResourceAsm extends ResourceAssemblerSupport<Vehicle, VehicleResource> {

    public VehicleResourceAsm() {
        super(VehicleController.class, VehicleResource.class);
    }

    @Override
    public VehicleResource toResource(Vehicle vehicle) {

        VehicleResource resource = new VehicleResource();
        resource.setLicensePlate(vehicle.getLicensePlate());
        resource.setName(vehicle.getName());
        resource.setRid(vehicle.getId());

        resource.add(linkTo(VehicleController.class).slash(vehicle.getId()).withSelfRel());
        resource.add(linkTo(VehicleController.class).slash(vehicle.getId()).slash("vehicles").withRel("entries"));
        if (vehicle.getOwner() != null) resource.add(linkTo(AccountController.class).slash(vehicle.getOwner().getId()).withRel("owner"));

        return resource;
    }
}
