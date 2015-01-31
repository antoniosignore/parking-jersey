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
public class Post implements Entity {

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

    @OneToOne
    @NotNull
    private Account owner;

    public Post() {
        this.date = new Date();
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        if (!id.equals(post.id)) return false;
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