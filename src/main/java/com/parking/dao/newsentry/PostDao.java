package com.parking.dao.newsentry;

import com.parking.dao.Dao;
import com.parking.entity.Post;
import com.parking.services.util.PostList;

import java.util.List;


public interface PostDao extends Dao<Post, Long> {

    public Post findPostByTitle(String content);
    public List<Post> findBlogsByAccountId(Long accountId) ;
    public List<Post> findAllRepliesByPostId(Long postId);

}