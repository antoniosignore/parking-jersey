package com.parking.rest.hateoas;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.parking.entity.Post;

import java.util.Collection;
import java.util.Date;


public class PostDto {

    private Long id;
    private String title;
    private String content;
    public PostDto() {
    }
    public PostDto(String title, Long id, String content) {
        this.title = title;
        this.id = id;
        this.content = content;
    }

    public static PostDto fromBean(Post book) {
        return new PostDto(book.getTitle(), book.getId(), book.getContent());
    }

    public static Collection<PostDto> fromBeanCollection(Collection<Post> posts) {
        return Collections2.transform(posts, new Function<Post, PostDto>() {
            @Override
            public PostDto apply(Post post) {
                return fromBean(post);
            }
        });
    }

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

    public Post toBean(PostDto book) {
        Post post = new Post();
        post.setTitle(book.getTitle());
        post.setContent(book.getContent());
        post.setDate(new Date());
        return post;
    }

}
