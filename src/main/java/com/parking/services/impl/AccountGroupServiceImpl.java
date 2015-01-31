package com.parking.services.impl;

import com.parking.dao.accountGroup.UserGroupDao;
import com.parking.entity.AccountGroup;
import com.parking.services.AccountGroupService;
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
    public void deleteAccountGroup(Long id) {
        userGroupDao.delete(id);
    }

    @Override
    public AccountGroup updateAccountGroupEntry(Long id, AccountGroup data) {
        return userGroupDao.save(data);
    }

}
