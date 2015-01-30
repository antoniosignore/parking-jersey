package com.parking.rest.hateoas;

import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class VehicleListResource extends ResourceSupport {

    private List<VehicleResource> vehicles = new ArrayList<VehicleResource>();

    public List<VehicleResource> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleResource> vehicles) {
        this.vehicles = vehicles;
    }
}
