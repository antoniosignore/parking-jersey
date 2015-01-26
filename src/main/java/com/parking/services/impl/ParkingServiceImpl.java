package com.parking.services.impl;

import com.parking.dao.parking.ParkingDao;
import com.parking.entity.Parking;
import com.parking.entity.Vehicle;
import com.parking.services.ParkingService;
import com.parking.services.util.ParkingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private ParkingDao parkingRepo;

    @Override
    public ParkingList findAllParkings() {
        return new ParkingList(parkingRepo.findAll());
    }

    @Override
    public Parking findParking(Long id) { return parkingRepo.find(id);
    }

    @Override
    public List<Parking> findParkingByAccountId(Long id) {
        return parkingRepo.findParkingsByAccount(id);
    }

    @Override
    public List<Parking> findParkingsByAccountName(String name) {
        return parkingRepo.findParkingsByAccountName(name);
    }

}
