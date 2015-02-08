package com.parking.rest.dto;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.parking.entity.Parking;
import com.parking.entity.ParkingStatusEnum;

import java.util.Collection;
import java.util.Date;

public class ParkingDto {

    ParkingStatusEnum status;

    Date whenPicked;

    Double latitude;

    Double longitude;

    Long pickedById;

    Long vehicleId;

    Long id;

    public ParkingDto() {
    }

    public ParkingDto(ParkingStatusEnum status, Date whenPicked, Double latitude, Double longitude, Long pickedById, Long vehicleId, Long id) {
        this.status = status;
        this.whenPicked = whenPicked;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pickedById = pickedById;
        this.vehicleId = vehicleId;
        this.id = id;
    }

    public Parking toBean(ParkingDto parkingDto) {
        return new Parking(parkingDto.getStatus(),
                parkingDto.getWhenPicked(),
                parkingDto.getLatitude(),
                parkingDto.getLongitude(),
                parkingDto.getId());
    }

    public static ParkingDto fromBean(Parking parking) {
        return new ParkingDto(parking.getStatus(), parking.getWhenPicked(), parking.getLatitude(),
                parking.getLongitude(), parking.getPickedBy().getId(),
                parking.getVehicle().getId(), parking.getId() );
    }

    public static Collection<ParkingDto> fromBeanCollection(Collection<Parking> vehicles) {
        return Collections2.transform(vehicles, new Function<Parking, ParkingDto>() {
            @Override
            public ParkingDto apply(Parking vehicle) {
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

    public ParkingStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ParkingStatusEnum status) {
        this.status = status;
    }

    public Date getWhenPicked() {
        return whenPicked;
    }

    public void setWhenPicked(Date whenPicked) {
        this.whenPicked = whenPicked;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getPickedById() {
        return pickedById;
    }

    public void setPickedById(Long pickedById) {
        this.pickedById = pickedById;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
}
