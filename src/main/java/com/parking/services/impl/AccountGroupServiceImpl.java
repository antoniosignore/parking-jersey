package com.parking.services.impl;

import com.parking.dao.accountGroup.AccountGroupDao;
import com.parking.entity.AccountGroup;
import com.parking.services.AccountGroupService;
import com.parking.services.util.AccountGroupList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountGroupServiceImpl implements AccountGroupService {

    @Autowired
    private AccountGroupDao accountGroupRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Override
    public AccountGroupList findAllGroups() {
        return new AccountGroupList(accountGroupRepo.findAll());
    }

    @Override
    public AccountGroup findAccountGroup(Long id) {
        return accountGroupRepo.find(id);
    }

//    @Override
//    public AccountGroup findAccountGroupEntry(Long id) {
//        return accountGroupRepo.findAccountGroup(id);
//    }

    @Override
    public void deleteAccountGroup(Long id) {
        accountGroupRepo.delete(id);
    }

    @Override
    public AccountGroup updateAccountGroupEntry(Long id, AccountGroup data) {
        return accountGroupRepo.save(data);
    }

}
