package com.parking.rest.hateoas;

import com.parking.entity.UserGroup;


public class AccountGroupDto {

    private String groupName;

    private String groupDescription;

    private Long rid;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public UserGroup toAccountGroup() {
        UserGroup accountGroup = new UserGroup();
        accountGroup.setGroupName(groupName);
        accountGroup.setGroupDesc(groupDescription);
        return accountGroup;
    }
}
