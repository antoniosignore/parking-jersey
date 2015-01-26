package com.parking.dao;

import com.parking.dao.user.UserDao;
import com.parking.dao.post.PostDao;
import com.parking.entity.Post;
import com.parking.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;


/**
 * Initialize the database with some test entries.
 *
 */
public class DataBaseInitializer {

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    protected DataBaseInitializer() {
        /* Default constructor for reflection instantiation */
    }

    public DataBaseInitializer(UserDao userDao, PostDao postDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.postDao = postDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void initDataBase() {
        User userUser = new User("user", this.passwordEncoder.encode("user"));
        userUser.addRole("user");
        this.userDao.save(userUser);

        User adminUser = new User("admin", this.passwordEncoder.encode("admin"));
        adminUser.addRole("user");
        adminUser.addRole("admin");
        this.userDao.save(adminUser);

//        long timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
//        for (int i = 0; i < 10; i++) {
//            Post post = new Post();
//            post.setContent("This is example content " + i);
//            post.setDate(new Date(timestamp));
//            this.postDao.save(post);
//            timestamp += 1000 * 60 * 60;
//        }
    }

}