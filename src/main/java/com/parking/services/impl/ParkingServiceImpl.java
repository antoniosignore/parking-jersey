package com.parking.services.impl;

import com.parking.core.models.entities.Parking;
import com.parking.core.repositories.ParkingRepo;
import com.parking.core.services.ParkingService;
import com.parking.core.services.util.ParkingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private ParkingRepo parkingRepo;

    @Override
    public ParkingList findAllParkings() {
        return new ParkingList(parkingRepo.findAllParkings());
    }

    @Override
    public Parking findParking(Long id) {
        return parkingRepo.findParking(id);
    }

    @Override
    public List<Parking> findParkingByAccountId(Long id) {
        return parkingRepo.findParkingsByAccount(id);
    }

    @Override
    public List<Parking> findParkingsByAccountName(String name) {
        return parkingRepo.findParkingsByAccountName(name);
    }

//    @Override
//    public Parking createParking(Long accountId, Parking data){
//        return parkingRepo.createParking(data);
//    }

}
