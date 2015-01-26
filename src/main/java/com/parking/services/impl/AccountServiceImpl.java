package com.parking.services.impl;

import com.parking.entity.*;
import com.parking.services.*;
import com.parking.services.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private AccountGroupRepo accountGroupRepo;

    @Autowired
    private ConnectionRepo connectionRepo;

    @Autowired
    private ParkingRepo parkingRepo;

    @Autowired
    private VehicleRepo vehicleRepo;

    @Override
    public Account findAccount(Long id) {
        return accountRepo.findAccount(id);
    }

    @Override
    public Account createAccount(Account data) {

        Account account = accountRepo.findAccountByName(data.getName());

        if (account != null) {
            throw new AccountExistsException();
        }

        return accountRepo.createAccount(data);
    }

    @Override
    public Blog createBlog(Long accountId, Blog blog) {

        Blog blogSameTitle = blogRepo.findBlogByTitle(blog.getTitle());

        if (blogSameTitle != null) throw new BlogExistsException();

        Account account = accountRepo.findAccount(accountId);

        if (account == null) throw new AccountDoesNotExistException();

        Blog createdBlog = blogRepo.createBlog(blog);

        createdBlog.setOwner(account);

        return createdBlog;
    }

    @Override
    public Connection createConnection(Long accountId, Long receiverId, Connection connection){

        Connection byInitiatorReceiver = connectionRepo.findByInitiatorReceiver(accountId, receiverId);
        if (byInitiatorReceiver != null) throw new ConnectionExistsException();

        Account initiator = accountRepo.findAccount(accountId);
        if (initiator == null) throw new AccountDoesNotExistException();

        Account receiver = accountRepo.findAccount(receiverId);
        if (receiver == null) throw new AccountDoesNotExistException();

        connection.setInitiator(initiator);
        connection.setReceiver(receiver);

        return connectionRepo.createConnection(connection);

    }

    @Override
    public Parking createParking(Long accountId, Parking parking){
        Account account = accountRepo.findAccount(accountId);
        if (account == null) throw new AccountDoesNotExistException();
        try {
            parking.setAccount(account);
            parking = parkingRepo.createParking(parking);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GroupExistsException();
        }
        return parking;
    }

    @Override
    public AccountGroup createAccountGroup(Long accountId, AccountGroup accountGroup) {
        Account account = accountRepo.findAccount(accountId);
        if (account == null) throw new AccountDoesNotExistException();
        accountGroup.addAccount(account);
        try {
            return accountGroupRepo.createAccountGroup(accountGroup);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GroupExistsException();
        }
    }

    @Override
    public Vehicle createVehicle(Long accountId, Vehicle vehicle) {
        Account account = accountRepo.findAccount(accountId);
        if (account == null) throw new AccountDoesNotExistException();
        vehicle.setOwner(account);
        try {
            return vehicleRepo.createVehicle(vehicle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GroupExistsException();
        }
    }

    @Override
    public BlogList findBlogsByAccount(Long accountId) {
        Account account = accountRepo.findAccount(accountId);
        if (account == null) {
            throw new AccountDoesNotExistException();
        }
        return new BlogList(blogRepo.findBlogsByAccountId(accountId));
    }

    @Override
    public ParkingList findParkingsByAccount(Long accountId) {
        Parking parking = parkingRepo.findParking(accountId);
        if (parking == null) {
            throw new ParkingDoesNotExistException();
        }
        return new ParkingList(parkingRepo.findParkingsByAccount(accountId));
    }

    @Override
    public ConnectionList findConnectionsByAccount(Long accountId) {
        Connection parking = connectionRepo.findConnection(accountId);
        if (parking == null) {
            throw new ConnectionDoesNotExistException();
        }
        return new ConnectionList(connectionRepo.findConnectionsByAccountId(accountId));
    }

    @Override
    public VehicleList findVehiclesByAccount(Long accountId) {
        Vehicle parking = vehicleRepo.findVehicle(accountId);
        if (parking == null) {
            throw new VehicleDoesNotExistException();
        }
        return new VehicleList(vehicleRepo.findVehiclesByAccountId(accountId));
    }

    @Override
    public AccountGroupList findAccountGroupsByAccount(Long accountId) {
        Account account = accountRepo.findAccount(accountId);
        if (account == null) {
            throw new AccountDoesNotExistException();
        }
        return new AccountGroupList(account.getAccountGroups());
    }

    @Override
    public AccountList findAllAccounts() {
        return new AccountList(accountRepo.findAllAccounts());
    }

    @Override
    public Account findByAccountName(String name) {
        return accountRepo.findAccountByName(name);
    }
}
