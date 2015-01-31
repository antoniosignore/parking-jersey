package com.parking.services;

import com.parking.entity.Parking;

import java.util.List;

public interface ParkingService {

    public List<Parking> findAllParkings();

    public Parking findParking(Long id);

    public List<Parking> findParkingByAccountId(Long id);

    public List<Parking> findParkingsByAccountName(String name);

//    public AccountGroup deleteParking(Long id);
//    public AccountGroup updateParking(Long id, Parking data);


}
