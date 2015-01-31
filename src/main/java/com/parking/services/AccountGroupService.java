package com.parking.services;


import com.parking.entity.AccountGroup;

import java.util.List;

public interface AccountGroupService {

    public List<AccountGroup> findAllGroups();

    public AccountGroup findAccountGroup(Long id);

    public AccountGroup updateAccountGroupEntry(Long id, AccountGroup data);

    public void deleteAccountGroup(Long id);

}
