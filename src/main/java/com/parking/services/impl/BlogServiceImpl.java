package com.parking.services.impl;

import com.parking.core.models.entities.Blog;
import com.parking.core.models.entities.BlogEntry;
import com.parking.core.repositories.BlogEntryRepo;
import com.parking.core.repositories.BlogRepo;
import com.parking.core.services.BlogService;
import com.parking.core.services.exceptions.BlogNotFoundException;
import com.parking.core.services.util.BlogEntryList;
import com.parking.core.services.util.BlogList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private BlogEntryRepo entryRepo;


    @Override
    public BlogEntry createBlogEntry(Long blogId, BlogEntry data) {
        Blog blog = blogRepo.findBlog(blogId);
        if (blog == null) {
            throw new BlogNotFoundException();
        }
        BlogEntry entry = entryRepo.createBlogEntry(data);
        entry.setBlog(blog);
        return entry;
    }

    @Override
    public BlogList findAllBlogs() {
        return new BlogList(blogRepo.findAllBlogs());
    }

    @Override
    public BlogEntryList findAllBlogEntries(Long blogId) {
        Blog blog = blogRepo.findBlog(blogId);
        if (blog == null) {
            throw new BlogNotFoundException();
        }
        return new BlogEntryList(blogId, entryRepo.findByBlogId(blogId));
    }

    @Override
    public Blog findBlog(Long id) {
        return blogRepo.findBlog(id);
    }
}
