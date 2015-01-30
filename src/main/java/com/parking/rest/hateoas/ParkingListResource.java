package com.parking.rest.hateoas;

import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class ParkingListResource extends ResourceSupport {

    private List<ParkingResource> parkings = new ArrayList<ParkingResource>();

    public List<ParkingResource> getParkings() {
        return parkings;
    }

    public void setParkings(List<ParkingResource> parkings) {
        this.parkings = parkings;
    }

}
