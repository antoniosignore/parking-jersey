package com.parking.model.dao;

import com.parking.model.Dao;
import com.parking.entity.Account;
import com.parking.entity.Parking;

import java.util.List;

public interface ParkingDao extends Dao<Parking, Long> {

    public List<Parking> findParkingsByAccount(Long id);

    public List<Parking> findParkingsByAccountName(String name);

    public List<Parking> findParkingsByAccount(Account loggedAccount) ;

    public Parking save(Account loggedAccount, Parking parking);

}