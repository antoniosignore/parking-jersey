package com.parking.services;


import com.parking.entity.*;
import com.parking.services.util.*;

public interface AccountService {

    public Account findUser(Long id);
    public Account findByUserName(String name);

    public Account createUser(Account data);

    public UserGroup createUserGroup(Long accountId, UserGroup data);
    public Post createPost(Long accountId, Post data);
    public Connection createConnection(Long accountId, Long receiverId, Connection data);
    public Parking createParking(Long accountId, Parking data);
    public Vehicle createVehicle(Long accountId, Vehicle data);

    public AccountList findAllUsers();
    public PostList findPostsByUser(Long userId);
    public ParkingList findParkingsByUser(Long accountId);
    public ConnectionList findConnectionsByUser(Long accountId);
    public AccountGroupList findUserGroupsByUser(Long accountId);
    public VehicleList findVehiclesByUser(Long accountId);
}
