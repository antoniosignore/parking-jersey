package com.parking.dao.user;

import com.parking.dao.Dao;
import com.parking.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserDao extends Dao<User, Long>, UserDetailsService {

    User findByName(String name);

}