package com.parking.services.impl;

import com.parking.entity.Account;
import com.parking.entity.BlogEntry;
import com.parking.model.dao.BlogEntryDao;
import com.parking.services.BlogEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BlogEntryServiceImpl implements BlogEntryService {

    @Autowired
    private BlogEntryDao blogEntryDao;

    @Override
    public BlogEntry createBlogEntry(Account account, BlogEntry blogEntry) {

        blogEntry.setOwner(account);

        BlogEntry createdBlog = blogEntryDao.save(blogEntry);
        createdBlog.setOwner(account);
        return createdBlog;
    }

    @Override
    public List<BlogEntry> findAllBlogEntries(Account account) {
        return blogEntryDao.findBlogsByAccount(account);
    }

    @Override
    public BlogEntry findBlogEntry(Long id) {
        return blogEntryDao.find(id);
    }

    @Override
    public BlogEntry update(BlogEntry blogEntry) {
        return blogEntryDao.save(blogEntry);
    }

    @Override
    public void delete(Long id) {
        blogEntryDao.delete(id);
    }

}
