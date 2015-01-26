package com.parking.dao.vehicle;

import com.parking.dao.Dao;
import com.parking.entity.Vehicle;

import java.util.List;


public interface VehicleDao extends Dao<Vehicle, Long> {

    List<Vehicle> findVehiclesByAccountName(Long name);

    List<Vehicle> findVehiclesByAccountId(Long name);

}