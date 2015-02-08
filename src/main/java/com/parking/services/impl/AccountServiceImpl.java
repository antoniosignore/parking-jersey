package com.parking.services.impl;

import com.parking.entity.Account;
import com.parking.model.dao.*;
import com.parking.services.AccountService;
import com.parking.services.exceptions.AccountExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private BlogEntryDao blogEntryDao;

    @Autowired
    private UserGroupDao userGroupDao;

    @Autowired
    private ConnectionDao connectionDao;

    @Autowired
    private ParkingDao parkingDao;

    @Autowired
    private VehicleDao vehicleDao;

    @Override
    public Account findAccount(Long id) {
        return accountDao.find(id);
    }

//    @Override
//    public Account findByName(String name) {
//        return null;
//    }

    @Override
    public Account createAccount(Account data) {
        Account account = accountDao.findByName(data.getName());
        if (account != null) throw new AccountExistsException();
        return accountDao.save(data);
    }

//    @Override
//    public Post createBlogEntry(Account account, Post post) {
//
//        post.setOwner(account);
//
//        Post createdBlog = postDao.update(post);
//        createdBlog.setOwner(account);
//        return createdBlog;
//    }
//
//    @Override
//    public Connection createConnection(Long userId, Long receiverId, Connection connection){
//
//        Connection byInitiatorReceiver = connectionDao.findByInitiatorIdReceiverId(userId, receiverId);
//        if (byInitiatorReceiver != null) throw new ConnectionExistsException();
//
//        Account initiator = accountDao.find(userId);
//        if (initiator == null) throw new AccountDoesNotExistException();
//
//        Account receiver = accountDao.find(receiverId);
//        if (receiver == null) throw new AccountDoesNotExistException();
//
//        connection.setInitiator(initiator);
//        connection.setReceiver(receiver);
//
//        return connectionDao.update(connection);
//    }
//
//    @Override
//    public Parking createParking(Long userId, Parking parking){
//        Account account = accountDao.find(userId);
//        if (account == null) throw new AccountDoesNotExistException();
//        try {
//            parking.setAccount(account);
//            parking = parkingDao.update(parking);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new GroupExistsException();
//        }
//        return parking;
//    }
//
//    @Override
//    public AccountGroup createUserGroup(Long userId, AccountGroup accountGroup) {
//        Account account = accountDao.find(userId);
//        if (account == null) throw new AccountDoesNotExistException();
//        accountGroup.setAccount(account);
//        try {
//            return userGroupDao.update(accountGroup);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new GroupExistsException();
//        }
//    }
//
//    @Override
//    public Vehicle createVehicle(Long userId, Vehicle vehicle) {
//        Account account = accountDao.find(userId);
//        if (account == null) throw new AccountDoesNotExistException();
//        vehicle.setOwner(account);
//        try {
//            return vehicleDao.update(vehicle);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new GroupExistsException();
//        }
//    }

//    @Override
//    public List<Post> findPostsByUser(Long userId) {
//        Account account = accountDao.find(userId);
//        if (account == null) {
//            throw new AccountDoesNotExistException();
//        }
//        return postDao.findBlogsByAccount(account);
//    }

    //    @Override
//    public List<Parking> findParkingsByUser(Long userId) {
//        Parking parking = parkingDao.find(userId);
//        if (parking == null) {
//            throw new ParkingDoesNotExistException();
//        }
//        return parkingDao.findParkingsByAccount(userId);
//    }
//
//    @Override
//    public ConnectionList findConnectionsByUser(Long userId) {
//        Connection parking = connectionDao.find(userId);
//        if (parking == null) {
//            throw new ConnectionDoesNotExistException();
//        }
//        return new List<Connection>(connectionDao.findConnectionsByAccountId(userId));
//    }
//
//    @Override
//    public VehicleList findVehiclesByUser(Long userId) {
//        Vehicle parking = vehicleDao.find(userId);
//        if (parking == null) {
//            throw new VehicleDoesNotExistException();
//        }
//        return new VehicleList(vehicleDao.findVehiclesByAccountId(userId));
//    }
//
//    @Override
//    public AccountGroupList findUserGroupsByUser(Long userId) {
//        Account account = accountDao.find(userId);
//        if (account == null) {
//            throw new AccountDoesNotExistException();
//        }
//        return new AccountGroupList(userGroupDao.findByUserId(userId));
//    }
//
//    @Override
//    public AccountList findAllUsers() {
//        return new AccountList(accountDao.findAll());
//    }
//
    @Override
    public Account findByName(String name) {
        return accountDao.findByName(name);
    }


    @Override
    public Account update(Account user) {
        return accountDao.save(user);
    }

    @Override
    public void delete(Long id) {
        accountDao.delete(id);
    }

}
