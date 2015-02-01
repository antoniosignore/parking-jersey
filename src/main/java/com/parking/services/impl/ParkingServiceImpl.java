package com.parking.services.impl;

import com.parking.model.dao.ParkingDao;
import com.parking.entity.Account;
import com.parking.entity.Parking;
import com.parking.services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private ParkingDao parkingDao;

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
    public Parking createParking(Account loggedAccount, Parking parking) {
        return parkingDao.save(loggedAccount, parking);
    }

    @Override
    public List<Parking> findAllParkingByAccount(Account loggedAccount) {
        return parkingDao.findParkingsByAccount(loggedAccount);
    }

    @Override
    public Parking update(Account loggedAccount, Parking parking) {
        parking.setAccount(loggedAccount);
        return parkingDao.save(parking);
    }

    @Override
    public void delete(Long id) {
        parkingDao.delete(id);
    }

}
