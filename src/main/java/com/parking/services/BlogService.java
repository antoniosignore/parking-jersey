package com.parking.services;

import com.parking.entity.Post;
import com.parking.services.util.PostList;

public interface BlogService {

    public Post createReply(Long blogId, Post reply);

    public PostList findAllBlogs();

    public PostList findAllReplies(Long blogId); // findBlog all associated blog entries

    public Post findBlog(Long id);

}
