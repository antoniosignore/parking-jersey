package com.parking.rest.hateoas;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.parking.entity.Post;

import java.util.Collection;


public class PostDto {

    public PostDto() {
    }

    public PostDto(String title, Long rid) {
        this.title = title;
        this.rid = rid;
    }

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

    public static PostDto fromBean(Post book) {
        return new PostDto(book.getTitle(), book.getId());
    }

    public static Collection<PostDto> fromBeanCollection(Collection<Post> books) {
        return Collections2.transform(books, new Function<Post, PostDto>() {
            @Override
            public PostDto apply(Post book) {
                return fromBean(book);
            }
        });
    }


}
