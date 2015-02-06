package com.parking.services;


import com.parking.entity.Account;
import com.parking.entity.AccountGroup;
import com.parking.entity.Vehicle;

import java.util.List;

public interface AccountGroupService {

    public List<AccountGroup> findAllGroups();

    public AccountGroup findAccountGroup(Long id);


    public List<AccountGroup> findAllAccountGroupByAccount(Account loggedAccount);

    AccountGroup createAccountGroup(Account loggedAccount, AccountGroup group);

    public AccountGroup update(AccountGroup post);

    public void delete(Long id);

}
