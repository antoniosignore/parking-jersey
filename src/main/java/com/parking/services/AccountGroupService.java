package com.parking.services;


import com.parking.entity.AccountGroup;
import com.parking.services.util.AccountGroupList;

public interface AccountGroupService {

    public AccountGroupList findAllGroups();

    public AccountGroup findAccountGroup(Long id);

    public AccountGroup updateAccountGroupEntry(Long id, AccountGroup data);

    public void deleteAccountGroup(Long id);

}
