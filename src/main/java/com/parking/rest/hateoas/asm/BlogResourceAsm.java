package com.parking.rest.hateoas.asm;

import com.parking.core.models.entities.Blog;
import com.rest.mvc.AccountController;
import com.rest.mvc.BlogController;
import com.rest.resources.BlogResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class BlogResourceAsm extends ResourceAssemblerSupport<Blog, BlogResource> {
    public BlogResourceAsm() {
        super(BlogController.class, BlogResource.class);
    }

    @Override
    public BlogResource toResource(Blog blog) {

        BlogResource resource = new BlogResource();
        resource.setTitle(blog.getTitle());
        resource.setRid(blog.getId());

        resource.add(linkTo(BlogController.class).slash(blog.getId()).withSelfRel());
        resource.add(linkTo(BlogController.class).slash(blog.getId()).slash("blog-entries").withRel("entries"));
        if (blog.getOwner() != null) resource.add(linkTo(AccountController.class).slash(blog.getOwner().getId()).withRel("owner"));
        return resource;

    }

}
