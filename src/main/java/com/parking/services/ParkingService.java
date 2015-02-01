package com.parking.services;

import com.parking.entity.Account;
import com.parking.entity.Parking;

import java.util.List;

public interface ParkingService {

    public List<Parking> findAllParkings();

    public Parking findParking(Long id);

    public List<Parking> findParkingByAccountId(Long id);

    public List<Parking> findParkingsByAccountName(String name);

    Parking createParking(Account loggedAccount, Parking parking);

    List<Parking> findAllParkingByAccount(Account loggedAccount);

    Parking update(Account loggedAccount, Parking parking);

    void delete(Long id);

}
