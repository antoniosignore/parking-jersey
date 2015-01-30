package com.parking.rest.hateoas.asm;

import com.parking.core.services.util.BlogList;
import com.rest.mvc.BlogController;
import com.rest.resources.BlogListResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;


public class BlogListResourceAsm extends ResourceAssemblerSupport<BlogList, BlogListResource> {

    public BlogListResourceAsm() {
        super(BlogController.class, BlogListResource.class);
    }

    @Override
    public BlogListResource toResource(BlogList blogList) {
        BlogListResource res = new BlogListResource();
        res.setBlogs(new BlogResourceAsm().toResources(blogList.getBlogs()));
        return res;
    }
}
