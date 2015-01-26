package com.parking.entity;

import com.parking.JsonViews;
import org.codehaus.jackson.map.annotate.JsonView;

import javax.persistence.*;
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
    private User owner;

    public Post() {
        this.date = new Date();
    }

    @JsonView(JsonViews.Admin.class)
    public Long getId() {
        return this.id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
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

        if (!date.equals(post.date)) return false;
        if (!id.equals(post.id)) return false;
        if (!owner.equals(post.owner)) return false;
        if (!title.equals(post.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + owner.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("NewsEntry[%d, %s]", this.id, this.content);
    }

}