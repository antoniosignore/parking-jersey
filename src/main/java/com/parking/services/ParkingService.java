package com.parking.services;

import com.parking.entity.Account;
import com.parking.entity.Parking;
import com.parking.entity.Vehicle;

import java.util.List;

public interface ParkingService {

    public List<Parking> findAllParkings();

    public Parking findParking(Long id);

    public List<Parking> findParkingByAccountId(Long id);

    public List<Parking> findParkingsByAccountName(String name);

    Parking createParking(Account loggedAccount, Vehicle vehicle, Parking parking);

    List<Parking> findAllParkingByAccount(Account account);

    Parking update(Parking parking);

    void delete(Long id);

}
