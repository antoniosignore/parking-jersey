package com.parking.model;

import com.parking.model.dao.AccountDao;
import com.parking.model.dao.PostDao;
import com.parking.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Initialize the database with some test entries.
 */
public class DataBaseInitializer {

    @Autowired
    private PostDao postDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    protected DataBaseInitializer() {
        /* Default constructor for reflection instantiation */
    }

    public DataBaseInitializer(AccountDao accountDao, PostDao postDao, PasswordEncoder passwordEncoder) {
        this.accountDao = accountDao;
        this.postDao = postDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void initDataBase() {
        Account userAccount = new Account("user", this.passwordEncoder.encode("user"));
        userAccount.addRole("user");
        this.accountDao.save(userAccount);

        Account adminAccount = new Account("admin", this.passwordEncoder.encode("admin"));
        adminAccount.addRole("user");
        adminAccount.addRole("admin");
        this.accountDao.save(adminAccount);

//        long timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
//        for (int i = 0; i < 10; i++) {
//            Post post = new Post();
//            post.setContent("This is example content " + i);
//            post.setDate(new Date(timestamp));
//            post.setOwner(userUser);
//            this.postDao.update(post);
//            timestamp += 1000 * 60 * 60;
//        }
    }

}