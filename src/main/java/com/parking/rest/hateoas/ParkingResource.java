package com.parking.rest.hateoas;

import com.parking.entity.Parking;
import com.parking.entity.ParkingStatusEnum;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

public class ParkingResource extends ResourceSupport {

    ParkingStatusEnum status;

    Date whenPicked;

    Double latitude;

    Double longitude;

    private Long rid;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
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

    public Parking toParking() {
        Parking parking = new Parking();
        parking.setLatitude(latitude);
        parking.setLongitude(longitude);
        parking.setWhenPicked(whenPicked);
        parking.setStatus(status);
        return parking;
    }
}
