package com.parking.services.impl;

import com.parking.entity.Account;
import com.parking.entity.Parking;
import com.parking.entity.ParkingStatusEnum;
import com.parking.entity.Vehicle;
import com.parking.model.dao.ParkingDao;
import com.parking.model.dao.VehicleDao;
import com.parking.services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private ParkingDao parkingDao;

    @Autowired
    private VehicleDao vehicleDao;

    @Override
    public List<Parking> findAllParkings() {
        return parkingDao.findAll();
    }

    @Override
    public Parking findParking(Long id) {
        return parkingDao.find(id);
    }

    @Override
    public List<Parking> findParkingByAccountId(Long id) {
        return parkingDao.findParkingsByAccount(id);
    }

    @Override
    public List<Parking> findParkingsByAccountName(String name) {
        return parkingDao.findParkingsByAccountName(name);
    }

    @Override
    public Parking createParking(Account loggedAccount, Vehicle vehicle, Parking parking) {
        parking.setStatus(ParkingStatusEnum.shared);
        parking.setParkingDate(new Date());
        parking.setVehicle(vehicle);
        parking.setAccount(loggedAccount);
        return parkingDao.save(parking);
    }

    @Override
    public List<Parking> findAllParkingByAccount(Account account) {
        return parkingDao.findParkingsByAccount(account);
    }

    @Override
    public Parking update(Parking parking) {
        return parkingDao.save(parking);
    }

    @Override
    public void delete(Long id) {
        parkingDao.delete(id);
    }

}
