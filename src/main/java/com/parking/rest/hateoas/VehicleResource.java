package com.parking.rest.hateoas;

import com.parking.entity.Vehicle;
import org.springframework.hateoas.ResourceSupport;

public class VehicleResource extends ResourceSupport {

    private String name;

    private String licensePlate;

    private Long rid;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Vehicle toVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName(this.name);
        vehicle.setLicensePlate(this.licensePlate);
        return vehicle;
    }
}
