package com.parking.rest.hateoas;

import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class PostListResource extends ResourceSupport {

    private List<PostResource> blogs = new ArrayList<PostResource>();

    public List<PostResource> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<PostResource> blogs) {
        this.blogs = blogs;
    }

}
