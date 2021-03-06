package com.parking.entity;

import com.parking.JsonViews;
import org.codehaus.jackson.map.annotate.JsonView;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@javax.persistence.Entity
public class BlogEntry implements Entity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotNull
    private Date date;

    @Column
    @NotNull
    private String title;

    @Column
    private String content;

    @Column
    private Integer views;

    @OneToOne
    @NotNull
    private Account owner;

    public BlogEntry() {
    }

    @JsonView(JsonViews.Admin.class)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(JsonViews.User.class)
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @JsonView(JsonViews.User.class)
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlogEntry)) return false;
        BlogEntry blogEntry = (BlogEntry) o;
        if (!id.equals(blogEntry.id)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("NewsEntry[%d, %s]", this.id, this.content);
    }

}