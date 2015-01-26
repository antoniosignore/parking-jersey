package com.parking.dao.account;

import com.parking.dao.Dao;
import com.parking.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface AccountDao extends Dao<Account, Long>, UserDetailsService {

    Account findByName(String name);

}