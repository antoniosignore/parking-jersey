package com.parking.services;

import com.parking.entity.Post;
import com.parking.services.util.PostList;

public interface PostService {

    public Post createReply(Long fatherPostId, Long accountId, Post reply);

    public PostList findAllPosts(Long accountId);

    public PostList findAllReplies(Long postId);

    public Post findPost(Long id);

}
