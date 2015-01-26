package com.parking.services.impl;

import com.parking.dao.accountGroup.UserGroupDao;
import com.parking.entity.UserGroup;
import com.parking.services.AccountGroupService;
import com.parking.services.util.AccountGroupList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountGroupServiceImpl implements AccountGroupService {

    @Autowired
    private UserGroupDao userGroupDao;

    @Override
    public AccountGroupList findAllGroups() {
        return new AccountGroupList(userGroupDao.findAll());
    }

    @Override
    public UserGroup findAccountGroup(Long id) {
        return userGroupDao.find(id);
    }

    @Override
    public void deleteAccountGroup(Long id) {
        userGroupDao.delete(id);
    }

    @Override
    public UserGroup updateAccountGroupEntry(Long id, UserGroup data) {
        return userGroupDao.save(data);
    }

}
