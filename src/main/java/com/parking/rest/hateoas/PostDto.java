package com.parking.rest.hateoas;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.parking.entity.Post;

import java.util.Collection;


public class PostDto {

    public PostDto() {
    }

    public PostDto(String title, Long id, String content) {
        this.title = title;
        this.id = id;
        this.content = content;
    }

    private Long id;
    private String title;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static PostDto fromBean(Post book) {

        return new PostDto(book.getTitle(), book.getId(), book.getContent());
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
