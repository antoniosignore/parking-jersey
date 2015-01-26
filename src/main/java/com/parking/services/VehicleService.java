package com.parking.services;

import com.parking.entity.Vehicle;
import com.parking.services.util.VehicleList;

import java.util.List;

public interface VehicleService {

    public VehicleList findAllVehicles();

    public Vehicle findVehicle(Long id);

    public List<Vehicle> findByAccountName(Long name);

    public List<Vehicle> findByAccountId(Long accountId) ;

}
