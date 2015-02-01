package com.parking.rest.hateoas;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.parking.entity.AccountGroup;

import java.util.Collection;


public class AccountGroupDto {

    private Long id;
    private String groupName;
    private String groupDescription;

    public AccountGroupDto() {
    }

    public AccountGroupDto(Long id, String groupName, String groupDescription) {
        this.id = id;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
    }

    public AccountGroup toBean (AccountGroupDto vehicleDto){
        AccountGroup group = new AccountGroup();
        group.setGroupName(vehicleDto.getGroupName());
        group.setGroupDesc(vehicleDto.getGroupDescription());
        return  group;
    }

    public static AccountGroupDto fromBean(AccountGroup group) {
        return new AccountGroupDto(group.getId(), group.getGroupName(), group.getGroupDesc());
    }

    public static Collection<AccountGroupDto> fromBeanCollection(Collection<AccountGroup> groups) {
        return Collections2.transform(groups, new Function<AccountGroup, AccountGroupDto>() {
            @Override
            public AccountGroupDto apply(AccountGroup group) {
                return fromBean(group);
            }
        });
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
