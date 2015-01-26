package com.parking.services.util;


import com.parking.entity.Post;

import java.util.ArrayList;
import java.util.List;

public class PostList {

    private List<Post> blogs = new ArrayList<Post>();

    public PostList(List resultList) {
        this.blogs = resultList;
    }

    public List<Post> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Post> blogs) {
        this.blogs = blogs;
    }
}
