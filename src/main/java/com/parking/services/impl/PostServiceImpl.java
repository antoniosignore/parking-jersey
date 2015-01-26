package com.parking.services.impl;

import com.parking.dao.post.PostDao;
import com.parking.dao.account.AccountDao;
import com.parking.entity.Post;
import com.parking.entity.Account;
import com.parking.services.PostService;
import com.parking.services.exceptions.BlogNotFoundException;
import com.parking.services.exceptions.UserDoesNotExistException;
import com.parking.services.util.PostList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    private AccountDao accountDao;

    @Override
    public Post createPost(Long accountId, Post reply) {

        Account poster = accountDao.find(accountId);
        if (poster == null) throw new UserDoesNotExistException();

        reply.setOwner(poster);
        Post entry = postDao.save(reply);
        return entry;
    }

    @Override
    public PostList findAllPosts(Long accountId) {
        return new PostList(postDao.findBlogsByAccountId(accountId));
    }

    @Override
    public PostList findAllReplies(Long postId) {
        Post blog = postDao.find(postId);
        if (blog == null) {
            throw new BlogNotFoundException();
        }
        return new PostList(postDao.findAllRepliesByPostId(postId));
    }

    @Override
    public Post findPost(Long id) {
        return postDao.find(id);
    }
}
