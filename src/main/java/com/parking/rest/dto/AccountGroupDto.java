package com.parking.rest.dto;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.parking.entity.AccountGroup;

import java.util.Collection;


public class AccountGroupDto {

    private Long id;
    private String name;
    private String description;

    public AccountGroupDto() {
    }

    public AccountGroupDto(Long id, String name, String groupDescription) {
        this.id = id;
        this.name = name;
        this.description = groupDescription;
    }

    public static AccountGroupDto fromBean(AccountGroup group) {
        return new AccountGroupDto(group.getId(), group.getName(), group.getDescription());
    }

    public static Collection<AccountGroupDto> fromBeanCollection(Collection<AccountGroup> groups) {
        return Collections2.transform(groups, new Function<AccountGroup, AccountGroupDto>() {
            @Override
            public AccountGroupDto apply(AccountGroup group) {
                return fromBean(group);
            }
        });
    }

    public AccountGroup toBean(AccountGroupDto accountGroupDto) {
        AccountGroup group = new AccountGroup();
        group.setName(accountGroupDto.getName());
        group.setDescription(accountGroupDto.getDescription());
        return group;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
