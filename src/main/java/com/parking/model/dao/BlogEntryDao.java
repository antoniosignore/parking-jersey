package com.parking.model.dao;

import com.parking.model.Dao;
import com.parking.entity.Account;
import com.parking.entity.BlogEntry;

import java.util.List;


public interface BlogEntryDao extends Dao<BlogEntry, Long> {

    public BlogEntry findPostByTitle(String content);

    public List<BlogEntry> findBlogsByAccount(Account accountId);
//    public List<Post> findAllRepliesByPostId(Long postId);

}