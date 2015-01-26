package com.parking.services.util;


import com.parking.entity.UserGroup;

import java.util.ArrayList;
import java.util.List;

public class AccountGroupList {

    private List<UserGroup> userGroups = new ArrayList<UserGroup>();

    public AccountGroupList(List<UserGroup> resultList) {
        this.userGroups = resultList;
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }
}
