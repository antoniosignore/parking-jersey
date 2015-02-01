package com.parking.rest.hateoas;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.parking.entity.Account;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Collection;

public class AccountDto {

    private Long id;
    private String name;
    private String password;
    private Double latitude;
    private Double longitude;

    public AccountDto() {
    }

    public AccountDto(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public static AccountDto fromBean(Account book) {
        return new AccountDto(book.getId(), book.getName(), book.getPassword());
    }

    public static Collection<AccountDto> fromBeanCollection(Collection<Account> accounts) {
        return Collections2.transform(accounts, new Function<Account, AccountDto>() {
            @Override
            public AccountDto apply(Account account) {
                return fromBean(account);
            }
        });
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

}
