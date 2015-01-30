package com.parking.rest.hateoas.asm;

import com.parking.core.services.util.ParkingList;
import com.rest.mvc.ParkingController;
import com.rest.resources.ParkingListResource;
import com.rest.resources.ParkingResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

public class ParkingListResourceAsm extends ResourceAssemblerSupport<ParkingList, ParkingListResource> {

    public ParkingListResourceAsm() {
        super(ParkingController.class, ParkingListResource.class);
    }

    @Override
    public ParkingListResource toResource(ParkingList parkingList) {
        List<ParkingResource> resList = new ParkingResourceAsm().toResources(parkingList.getParkings());
        ParkingListResource finalRes = new ParkingListResource();
        finalRes.setParkings(resList);
        return finalRes;
    }
}
