package com.parking.services;


import com.parking.entity.*;
import com.parking.services.util.*;

public interface AccountService {

    public User findAccount(Long id);
    public User findByAccountName(String name);

    public User createAccount(User data);

    public AccountGroup createAccountGroup(Long accountId, AccountGroup data);
    public Post createBlog(Long accountId, Post data);
    public Connection createConnection(Long accountId, Long receiverId, Connection data);
    public Parking createParking(Long accountId, Parking data);
    public Vehicle createVehicle(Long accountId, Vehicle data);

    public AccountList findAllAccounts();
    public PostList findBlogsByAccount(Long accountId);
    public ParkingList findParkingsByAccount(Long accountId);
    public ConnectionList findConnectionsByAccount(Long accountId);
    public AccountGroupList findAccountGroupsByAccount(Long accountId);
    public VehicleList findVehiclesByAccount(Long accountId);
}
