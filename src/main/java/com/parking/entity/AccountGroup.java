package com.parking.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@javax.persistence.Entity
public class AccountGroup implements Entity, Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    private String description;

    @ManyToOne
    private Account account;

    public AccountGroup() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String groupName) {
        this.name = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String groupDesc) {
        this.description = groupDesc;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountGroup)) return false;

        AccountGroup that = (AccountGroup) o;

        if (!account.equals(that.account)) return false;
        if (!description.equals(that.description)) return false;
        if (!name.equals(that.name)) return false;
        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + account.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AccountGroup{" +
                "id=" + id +
                ", groupName='" + name + '\'' +
                ", groupDesc='" + description + '\'' +
                ", account=" + account +
                '}';
    }
}