package com.parking.services.impl;

import com.parking.model.dao.VehicleDao;
import com.parking.entity.Account;
import com.parking.entity.Vehicle;
import com.parking.services.VehicleService;
import com.parking.services.exceptions.GroupExistsException;
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
    public List<Vehicle> findAllVehicles() {
        return vehicleDao.findAll();
    }

    @Override
    public Vehicle findVehicle(Long id) {
        return vehicleDao.find(id);
    }

    @Override
    public List<Vehicle> findAllVehicleByAccount(Account account) {
        return vehicleDao.findVehiclesByAccount(account);
    }

    @Override
    public List<Vehicle> findByAccountName(Long name) {
        return vehicleDao.findVehiclesByAccountName(name);
    }

    @Override
    public List<Vehicle> findByAccountId(Long id) {
        return vehicleDao.findVehiclesByAccountName(id);
    }

    @Override
    public Vehicle createVehicle(Account loggedAccount, Vehicle vehicle) {
        vehicle.setOwner(loggedAccount);
        try {
            return vehicleDao.save(vehicle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GroupExistsException();
        }
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        return vehicleDao.save(vehicle);
    }

    @Override
    public void delete(Long id) {
        vehicleDao.delete(id);
    }
}
