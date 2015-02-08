package com.parking.rest.dto;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.parking.entity.BlogEntry;

import java.util.Collection;
import java.util.Date;


public class BlogEntryDto {

    private Long id;
    private String title;
    private String content;

    public BlogEntryDto() {
    }

    public BlogEntryDto(String title, Long id, String content) {
        this.title = title;
        this.id = id;
        this.content = content;
    }

    public static BlogEntryDto fromBean(BlogEntry book) {
        return new BlogEntryDto(book.getTitle(), book.getId(), book.getContent());
    }

    public static Collection<BlogEntryDto> fromBeanCollection(Collection<BlogEntry> blogEntries) {
        return Collections2.transform(blogEntries, new Function<BlogEntry, BlogEntryDto>() {
            @Override
            public BlogEntryDto apply(BlogEntry blogEntry) {
                return fromBean(blogEntry);
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

    public BlogEntry toBean(BlogEntryDto book) {
        BlogEntry blogEntry = new BlogEntry();
        blogEntry.setTitle(book.getTitle());
        blogEntry.setContent(book.getContent());
        blogEntry.setDate(new Date());
        return blogEntry;
    }

}
