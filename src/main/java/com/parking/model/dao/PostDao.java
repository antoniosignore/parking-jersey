package com.parking.model.dao;

import com.parking.model.Dao;
import com.parking.entity.Account;
import com.parking.entity.Post;

import java.util.List;


public interface PostDao extends Dao<Post, Long> {

    public Post findPostByTitle(String content);

    public List<Post> findBlogsByAccount(Account accountId);
//    public List<Post> findAllRepliesByPostId(Long postId);

}