package com.parking.services.util;


import com.parking.entity.User;

import java.util.ArrayList;
import java.util.List;

public class AccountList {

    private List<User> accounts = new ArrayList<User>();

    public AccountList(List<User> list) {
        this.accounts = list;
    }

    public List<User> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<User> accounts) {
        this.accounts = accounts;
    }

}
