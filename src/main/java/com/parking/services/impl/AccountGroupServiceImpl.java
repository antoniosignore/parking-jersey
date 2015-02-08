package com.parking.services.impl;

import com.parking.entity.Account;
import com.parking.entity.AccountGroup;
import com.parking.model.dao.UserGroupDao;
import com.parking.services.AccountGroupService;
import com.parking.services.exceptions.GroupExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountGroupServiceImpl implements AccountGroupService {

    @Autowired
    private UserGroupDao userGroupDao;

    @Override
    public List<AccountGroup> findAllGroups() {
        return userGroupDao.findAll();
    }

    @Override
    public AccountGroup findAccountGroup(Long id) {
        return userGroupDao.find(id);
    }

    @Override
    public List<AccountGroup> findAllAccountGroupByAccount(Account loggedAccount) {
        return userGroupDao.findByUser(loggedAccount);
    }

    @Override
    public AccountGroup createAccountGroup(Account loggedAccount, AccountGroup userGroup) {
        userGroup.setAccount(loggedAccount);
        try {
            return userGroupDao.save(userGroup);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GroupExistsException();
        }
    }

    @Override
    public AccountGroup update(AccountGroup vehicle) {
        return userGroupDao.save(vehicle);
    }

    @Override
    public void delete(Long id) {
        userGroupDao.delete(id);
    }

}
