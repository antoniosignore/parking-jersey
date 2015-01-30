package com.parking.rest.hateoas;

import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class UserGroupListResource extends ResourceSupport {

    private List<AccountGroupResource> groups = new ArrayList<AccountGroupResource>();

    public List<AccountGroupResource> getGroups() {
        return groups;
    }

    public void setGroups(List<AccountGroupResource> groups) {
        this.groups = groups;
    }

}
