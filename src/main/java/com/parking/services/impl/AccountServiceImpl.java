package com.parking.services.impl;

import com.parking.dao.accountGroup.UserGroupDao;
import com.parking.dao.connection.ConnectionDao;
import com.parking.dao.post.PostDao;
import com.parking.dao.parking.ParkingDao;
import com.parking.dao.account.AccountDao;
import com.parking.dao.vehicle.VehicleDao;
import com.parking.entity.*;
import com.parking.services.AccountService;
import com.parking.services.exceptions.*;
import com.parking.services.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserGroupDao userGroupDao;

    @Autowired
    private ConnectionDao connectionDao;

    @Autowired
    private ParkingDao parkingDao;

    @Autowired
    private VehicleDao vehicleDao;

    @Override
    public Account findUser(Long id) {
        return accountDao.find(id);
    }

    @Override
    public Account createUser(Account data) {
        Account account = accountDao.findByName(data.getName());
        if (account != null) throw new AccountExistsException();
        return accountDao.save(data);
    }

    @Override
    public Post createPost(Long userId, Post blog) {

        Post blogSameTitle = postDao.findPostByTitle(blog.getContent());
        if (blogSameTitle != null) throw new BlogExistsException();
        Account account = accountDao.find(userId);
        if (account == null) throw new AccountDoesNotExistException();
        Post createdBlog = postDao.save(blog);
        createdBlog.setOwner(account);
        return createdBlog;
    }

    @Override
    public Connection createConnection(Long userId, Long receiverId, Connection connection){

        Connection byInitiatorReceiver = connectionDao.findByInitiatorReceiver(userId, receiverId);
        if (byInitiatorReceiver != null) throw new ConnectionExistsException();

        Account initiator = accountDao.find(userId);
        if (initiator == null) throw new AccountDoesNotExistException();

        Account receiver = accountDao.find(receiverId);
        if (receiver == null) throw new AccountDoesNotExistException();

        connection.setInitiator(initiator);
        connection.setReceiver(receiver);

        return connectionDao.save(connection);
    }

    @Override
    public Parking createParking(Long userId, Parking parking){
        Account account = accountDao.find(userId);
        if (account == null) throw new AccountDoesNotExistException();
        try {
            parking.setAccount(account);
            parking = parkingDao.save(parking);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GroupExistsException();
        }
        return parking;
    }

    @Override
    public UserGroup createUserGroup(Long userId, UserGroup userGroup) {
        Account account = accountDao.find(userId);
        if (account == null) throw new AccountDoesNotExistException();
        userGroup.setAccount(account);
        try {
            return userGroupDao.save(userGroup);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GroupExistsException();
        }
    }

    @Override
    public Vehicle createVehicle(Long userId, Vehicle vehicle) {
        Account account = accountDao.find(userId);
        if (account == null) throw new AccountDoesNotExistException();
        vehicle.setOwner(account);
        try {
            return vehicleDao.save(vehicle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GroupExistsException();
        }
    }

    @Override
    public PostList findPostsByUser(Long userId) {
        Account account = accountDao.find(userId);
        if (account == null) {
            throw new AccountDoesNotExistException();
        }
        return new PostList(postDao.findBlogsByAccountId(userId));
    }

    @Override
    public ParkingList findParkingsByUser(Long userId) {
        Parking parking = parkingDao.find(userId);
        if (parking == null) {
            throw new ParkingDoesNotExistException();
        }
        return new ParkingList(parkingDao.findParkingsByAccount(userId));
    }

    @Override
    public ConnectionList findConnectionsByUser(Long userId) {
        Connection parking = connectionDao.find(userId);
        if (parking == null) {
            throw new ConnectionDoesNotExistException();
        }
        return new ConnectionList(connectionDao.findConnectionsByAccountId(userId));
    }

    @Override
    public VehicleList findVehiclesByUser(Long userId) {
        Vehicle parking = vehicleDao.find(userId);
        if (parking == null) {
            throw new VehicleDoesNotExistException();
        }
        return new VehicleList(vehicleDao.findVehiclesByAccountId(userId));
    }

    @Override
    public AccountGroupList findUserGroupsByUser(Long userId) {
        Account account = accountDao.find(userId);
        if (account == null) {
            throw new AccountDoesNotExistException();
        }
        return new AccountGroupList(userGroupDao.findByUserId(userId));
    }

    @Override
    public AccountList findAllUsers() {
        return new AccountList(accountDao.findAll());
    }

    @Override
    public Account findByUserName(String name) {
        return accountDao.findByName(name);
    }
}
