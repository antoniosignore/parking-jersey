package com.parking.rest.hateoas;

import com.parking.entity.Post;
import org.springframework.hateoas.ResourceSupport;

public class PostResource extends ResourceSupport {

    private String title;

    private Long rid;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Post toBlog() {
        Post blog = new Post();
        blog.setTitle(title);
        return blog;
    }

}
