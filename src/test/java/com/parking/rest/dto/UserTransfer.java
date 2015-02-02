package com.parking.rest.dto;

import java.util.Map;

public class UserTransfer {

    private String name;

    private Map<String, Boolean> roles;


    public UserTransfer() {
    }

    public String getName() {
        return name;
    }

    public Map<String, Boolean> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "UserTransfer{" +
                "name='" + name + '\'' +
                ", roles=" + roles +
                '}';
    }
}