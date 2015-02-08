package com.parking.services;

import com.parking.entity.Account;
import com.parking.entity.BlogEntry;

import java.util.List;

public interface BlogEntryService {

    public BlogEntry createBlogEntry(Account account, BlogEntry reply);

    public List<BlogEntry> findAllBlogEntries(Account account);

    public BlogEntry findBlogEntry(Long id);

    public BlogEntry update(BlogEntry blogEntry);

    public void delete(Long id);

}
