package com.parking.services;


import com.parking.entity.UserGroup;
import com.parking.services.util.AccountGroupList;

public interface AccountGroupService {

    public AccountGroupList findAllGroups();

    public UserGroup findAccountGroup(Long id);

    public UserGroup updateAccountGroupEntry(Long id, UserGroup data);

    public void deleteAccountGroup(Long id);

}
