package com.parking.dao.post;

import com.parking.dao.Dao;
import com.parking.entity.Post;

import java.util.List;


public interface PostDao extends Dao<Post, Long> {

    public Post findPostByTitle(String content);
    public List<Post> findBlogsByAccountId(Long accountId) ;
    public List<Post> findAllRepliesByPostId(Long postId);

}