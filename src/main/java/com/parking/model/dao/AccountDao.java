package com.parking.model.dao;

import com.parking.model.Dao;
import com.parking.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface AccountDao extends Dao<Account, Long>, UserDetailsService {

    Account findByName(String name);

}