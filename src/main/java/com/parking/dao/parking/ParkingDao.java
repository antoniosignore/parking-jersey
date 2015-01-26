package com.parking.dao.parking;

import com.parking.entity.Parking;
import com.parking.dao.Dao;

import java.util.List;


public interface ParkingDao extends Dao<Parking, Long> {

    List<Parking> findParkingsByAccount(Long id);

    List<Parking> findParkingsByAccountName(String name);
}