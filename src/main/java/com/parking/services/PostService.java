package com.parking.services;

import com.parking.entity.Account;
import com.parking.entity.Post;

import java.util.List;

public interface PostService {

    public Post createPost(Account account, Post reply);

    public List<Post> findAllPosts(Account account);

    public Post findPost(Long id);

    public Post save(Long id, Post post);

    public void delete(Long id);

}
