package com.parking.model.dao;

import com.parking.entity.Account;
import com.parking.entity.Vehicle;
import com.parking.model.Dao;

import java.util.List;


public interface VehicleDao extends Dao<Vehicle, Long> {

    List<Vehicle> findVehiclesByAccountName(Long name);

    List<Vehicle> findVehiclesByAccountId(Long name);

    List<Vehicle> findVehiclesByAccount(Account account);
}