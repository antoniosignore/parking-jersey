package com.parking.dao.parking;

import com.parking.dao.Dao;
import com.parking.entity.Parking;

import java.util.List;


public interface ParkingDao extends Dao<Parking, Long> {

    List<Parking> findParkingsByAccount(Long id);

    List<Parking> findParkingsByAccountName(String name);
}