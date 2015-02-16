package com.parking.rest.dto;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.parking.entity.Vehicle;

import java.util.Collection;
import java.util.Date;


public class VehicleDto {

    private Long id;
    private String name;
    private String licensePlate;
    private Date created_at;

    public VehicleDto() {
    }

    public VehicleDto(String name, String licensePlate, Long id, Date created_at) {
        this.name = name;
        this.licensePlate = licensePlate;
        this.id = id;
        this.created_at = created_at;
    }

    public static VehicleDto fromBean(Vehicle vehicle) {
        return new VehicleDto(vehicle.getName(), vehicle.getLicensePlate(), vehicle.getId(), vehicle.getCreated_at());
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Vehicle toBean(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setName(vehicleDto.getName());
        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        vehicle.setCreated_at(vehicleDto.getCreated_at());
        return vehicle;
    }
}
