package com.parking.rest.hateoas;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.parking.entity.Vehicle;

import java.util.Collection;


public class VehicleDto {

    private Long id;
    private String name;
    private String licensePlate;

    public VehicleDto() {
    }

    public VehicleDto(String name, String licensePlate, Long id) {
        this.name = name;
        this.licensePlate = licensePlate;
        this.id = id;
    }

    public static VehicleDto fromBean(Vehicle vehicle) {
        return new VehicleDto(vehicle.getName(), vehicle.getLicensePlate(), vehicle.getId());
    }

    public static Collection<VehicleDto> fromBeanCollection(Collection<Vehicle> vehicles) {
        return Collections2.transform(vehicles, new Function<Vehicle, VehicleDto>() {
            @Override
            public VehicleDto apply(Vehicle vehicle) {
                return fromBean(vehicle);
            }
        });
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Vehicle toBean(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setName(vehicleDto.getName());
        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        return vehicle;
    }
}
