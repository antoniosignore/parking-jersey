package com.parking.dao.accountGroup;

import com.parking.dao.Dao;
import com.parking.entity.UserGroup;

import java.util.List;


public interface UserGroupDao extends Dao<UserGroup, Long> {

    List<UserGroup> findByUserId(Long userId);

}