package com.parking.dao.accountGroup;

import com.parking.dao.Dao;
import com.parking.entity.Account;
import com.parking.entity.AccountGroup;

import java.util.List;


public interface UserGroupDao extends Dao<AccountGroup, Long> {

    List<AccountGroup> findByUserId(Long userId);

    List<AccountGroup> findByUser(Account loggedAccount);
}