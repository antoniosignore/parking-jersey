package com.parking.services;

import com.parking.entity.Account;
import com.parking.entity.Vehicle;

public interface AccountService {

    public Account findAccount(Long id);

    public Account findByName(String name);

    public Account createAccount(Account data);

//    public AccountGroup createUserGroup(Long accountId, AccountGroup data);
//    public Post createBlogEntry(Account account, Post data);
//    public Connection createConnection(Long accountId, Long receiverId, Connection data);
//    public Parking createParking(Long accountId, Parking data);
//    public Vehicle createVehicle(Long accountId, Vehicle data);
//
//    public AccountList findAllUsers();
//    public PostList findPostsByUser(Long userId);
//    public ParkingList findParkingsByUser(Long accountId);
//    public ConnectionList findConnectionsByUser(Long accountId);
//    public AccountGroupList findUserGroupsByUser(Long accountId);
//    public VehicleList findVehiclesByUser(Long accountId);


    public Account update(Account user);

    public void delete(Long id);


}



