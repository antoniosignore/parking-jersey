package com.parking.rest.hateoas;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.parking.entity.Connection;

import java.util.Collection;

public class ConnectionDto {

    private Long id;

    private Boolean confirmed;

    public ConnectionDto() {
    }

    public ConnectionDto(Long id, Boolean confirmed) {
        this.id = id;
        this.confirmed = confirmed;
    }

    public static ConnectionDto fromBean(Connection connection) {
        return new ConnectionDto(connection.getId(), connection.getConfirmed());
    }

    public static Collection<ConnectionDto> fromBeanCollection(Collection<Connection> connections) {
        return Collections2.transform(connections, new Function<Connection, ConnectionDto>() {
            @Override
            public ConnectionDto apply(Connection connection) {
                return fromBean(connection);
            }
        });
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

}
