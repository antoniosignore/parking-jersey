package com.parking.services.impl;

import com.parking.dao.vehicle.VehicleDao;
import com.parking.entity.Vehicle;
import com.parking.services.VehicleService;
import com.parking.services.util.VehicleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleDao vehicleDao;

    @Override
    public VehicleList findAllVehicles() {
        return new VehicleList(vehicleDao.findAll());
    }

    @Override
    public Vehicle findVehicle(Long id) {
        return vehicleDao.find(id);
    }

    @Override
    public List<Vehicle> findByAccountName(Long name) {
        return vehicleDao.findVehiclesByAccountName(name);
    }

    @Override
    public List<Vehicle> findByAccountId(Long accountId) {
        return vehicleDao.findVehiclesByAccountName(accountId);
    }

}
