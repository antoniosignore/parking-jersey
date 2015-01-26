package com.parking.services.impl;

import com.parking.core.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;

public class RepositoriesImpl {

    @Autowired
    protected AccountRepo accountRepo;

    @Autowired
    protected BlogRepo blogRepo;

    @Autowired
    protected AccountGroupRepo accountGroupRepo;

    @Autowired
    protected ConnectionRepo connectionRepo;

    @Autowired
    protected ParkingRepo parkingRepo;

    public AccountRepo getAccountRepo() {
        return accountRepo;
    }

    public void setAccountRepo(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public BlogRepo getBlogRepo() {
        return blogRepo;
    }

    public void setBlogRepo(BlogRepo blogRepo) {
        this.blogRepo = blogRepo;
    }

    public AccountGroupRepo getAccountGroupRepo() {
        return accountGroupRepo;
    }

    public void setAccountGroupRepo(AccountGroupRepo accountGroupRepo) {
        this.accountGroupRepo = accountGroupRepo;
    }

    public ConnectionRepo getConnectionRepo() {
        return connectionRepo;
    }

    public void setConnectionRepo(ConnectionRepo connectionRepo) {
        this.connectionRepo = connectionRepo;
    }

    public ParkingRepo getParkingRepo() {
        return parkingRepo;
    }

    public void setParkingRepo(ParkingRepo parkingRepo) {
        this.parkingRepo = parkingRepo;
    }
}
