package com.parking.services;

import com.parking.entity.Account;
import com.parking.entity.Vehicle;

import java.util.List;

public interface VehicleService {

    public List<Vehicle> findAllVehicles();

    public Vehicle findVehicle(Long id);

    public List<Vehicle> findAllVehicleByAccount(Account account);

    public List<Vehicle> findByAccountName(Long name);

    public List<Vehicle> findByAccountId(Long accountId);

    public Vehicle createVehicle(Account loggedAccount, Vehicle vehicle);

    public Vehicle save(Long id, Vehicle post);

    public void delete(Long id);

}
