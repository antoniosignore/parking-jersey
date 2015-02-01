package com.parking.services.impl;

import com.parking.model.dao.PostDao;
import com.parking.entity.Account;
import com.parking.entity.Post;
import com.parking.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Override
    public Post createPost(Account account, Post post) {

        post.setOwner(account);

        Post createdBlog = postDao.save(post);
        createdBlog.setOwner(account);
        return createdBlog;
    }

    @Override
    public List<Post> findAllPosts(Account account) {
        return postDao.findBlogsByAccount(account);
    }

    @Override
    public Post findPost(Long id) {
        return postDao.find(id);
    }

    @Override
    public Post save(Long id, Post post) {
        return postDao.save(post);
    }

    @Override
    public void delete(Long id) {
        postDao.delete(id);
    }

}
