package com.parking.services.impl;

import com.parking.dao.newsentry.PostDao;
import com.parking.dao.user.UserDao;
import com.parking.entity.Post;
import com.parking.entity.User;
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
    private PostDao blogRepo;

    @Autowired
    private UserDao userRepo;

    @Override
    public Post createReply(Long fatherPostId, Long accountId, Post reply) {

        Post blog = blogRepo.find(fatherPostId);
        if (blog == null) throw new BlogNotFoundException();
        reply.setReplyTo(blog);

        User poster = userRepo.find(accountId);
        if (poster == null) throw new UserDoesNotExistException();

        reply.setOwner(poster);
        Post entry = blogRepo.save(reply);
        return entry;
    }

    @Override
    public PostList findAllPosts(Long accountId) {
        return new PostList(blogRepo.findBlogsByAccountId(accountId));
    }

    @Override
    public PostList findAllReplies(Long postId) {
        Post blog = blogRepo.find(postId);
        if (blog == null) {
            throw new BlogNotFoundException();
        }
        return new PostList(blogRepo.findAllRepliesByPostId(postId));
    }

    @Override
    public Post findPost(Long id) {
        return blogRepo.find(id);
    }
}
