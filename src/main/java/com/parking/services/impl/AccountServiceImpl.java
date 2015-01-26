package com.parking.services.impl;

import com.parking.dao.accountGroup.UserGroupDao;
import com.parking.dao.connection.ConnectionDao;
import com.parking.dao.post.PostDao;
import com.parking.dao.parking.ParkingDao;
import com.parking.dao.user.UserDao;
import com.parking.dao.vehicle.VehicleDao;
import com.parking.entity.*;
import com.parking.services.UserService;
import com.parking.services.exceptions.*;
import com.parking.services.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao blogRepo;

    @Autowired
    private UserGroupDao userGroupRepo;

    @Autowired
    private ConnectionDao connectionRepo;

    @Autowired
    private ParkingDao parkingRepo;

    @Autowired
    private VehicleDao vehicleRepo;

    @Override
    public User findUser(Long id) {
        return userDao.find(id);
    }

    @Override
    public User createUser(User data) {
        User user = userDao.findByName(data.getName());
        if (user != null) throw new AccountExistsException();
        return userDao.save(data);
    }

    @Override
    public Post createPost(Long userId, Post blog) {

        Post blogSameTitle = blogRepo.findPostByTitle(blog.getContent());
        if (blogSameTitle != null) throw new BlogExistsException();
        User user = userDao.find(userId);
        if (user == null) throw new UserDoesNotExistException();
        Post createdBlog = blogRepo.save(blog);
        createdBlog.setOwner(user);
        return createdBlog;
    }

    @Override
    public Connection createConnection(Long userId, Long receiverId, Connection connection){

        Connection byInitiatorReceiver = connectionRepo.findByInitiatorReceiver(userId, receiverId);
        if (byInitiatorReceiver != null) throw new ConnectionExistsException();

        User initiator = userDao.find(userId);
        if (initiator == null) throw new UserDoesNotExistException();

        User receiver = userDao.find(receiverId);
        if (receiver == null) throw new UserDoesNotExistException();

        connection.setInitiator(initiator);
        connection.setReceiver(receiver);

        return connectionRepo.save(connection);
    }

    @Override
    public Parking createParking(Long userId, Parking parking){
        User user = userDao.find(userId);
        if (user == null) throw new UserDoesNotExistException();
        try {
            parking.setAccount(user);
            parking = parkingRepo.save(parking);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GroupExistsException();
        }
        return parking;
    }

    @Override
    public UserGroup createUserGroup(Long userId, UserGroup userGroup) {
        User user = userDao.find(userId);
        if (user == null) throw new UserDoesNotExistException();
        userGroup.setAccount(user);
        try {
            return userGroupRepo.save(userGroup);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GroupExistsException();
        }
    }

    @Override
    public Vehicle createVehicle(Long userId, Vehicle vehicle) {
        User user = userDao.find(userId);
        if (user == null) throw new UserDoesNotExistException();
        vehicle.setOwner(user);
        try {
            return vehicleRepo.save(vehicle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GroupExistsException();
        }
    }

    @Override
    public PostList findPostsByUser(Long userId) {
        User user = userDao.find(userId);
        if (user == null) {
            throw new UserDoesNotExistException();
        }
        return new PostList(blogRepo.findBlogsByAccountId(userId));
    }

    @Override
    public ParkingList findParkingsByUser(Long userId) {
        Parking parking = parkingRepo.find(userId);
        if (parking == null) {
            throw new ParkingDoesNotExistException();
        }
        return new ParkingList(parkingRepo.findParkingsByAccount(userId));
    }

    @Override
    public ConnectionList findConnectionsByUser(Long userId) {
        Connection parking = connectionRepo.find(userId);
        if (parking == null) {
            throw new ConnectionDoesNotExistException();
        }
        return new ConnectionList(connectionRepo.findConnectionsByAccountId(userId));
    }

    @Override
    public VehicleList findVehiclesByUser(Long userId) {
        Vehicle parking = vehicleRepo.find(userId);
        if (parking == null) {
            throw new VehicleDoesNotExistException();
        }
        return new VehicleList(vehicleRepo.findVehiclesByAccountId(userId));
    }

    @Override
    public AccountGroupList findUserGroupsByUser(Long userId) {
        User user = userDao.find(userId);
        if (user == null) {
            throw new UserDoesNotExistException();
        }
        return new AccountGroupList(userGroupRepo.findByUserId(userId));
    }

    @Override
    public AccountList findAllUsers() {
        return new AccountList(userDao.findAll());
    }

    @Override
    public User findByUserName(String name) {
        return userDao.findByName(name);
    }
}
