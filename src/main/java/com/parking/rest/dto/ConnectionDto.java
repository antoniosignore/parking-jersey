package com.parking.rest.dto;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.parking.entity.Connection;

import java.util.Collection;

public class ConnectionDto {

    private Long id;

    private Boolean confirmed;

    private Long initiatorId;

    private Long receiverId;

    private Long initiatorGroupId;

    private Long receiverGroupId;

    public ConnectionDto() {
    }

    public ConnectionDto(Long id, Boolean confirmed, Long initiatorId, Long receiverId, Long initiatorGroupId, Long receiverGroupId) {
        this.id = id;
        this.confirmed = confirmed;
        this.initiatorId = initiatorId;
        this.receiverId = receiverId;
        this.initiatorGroupId = initiatorGroupId;
        this.receiverGroupId = receiverGroupId;
    }

    public static ConnectionDto fromBean(Connection connection) {
        return (
        new ConnectionDto(connection.getId(), connection.getConfirmed(), connection.getInitiator().getId(),
                connection.getReceiver().getId(), connection.getInitiatorGroup().getId(), connection.getReceiverGroup().getId()));
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

    public Boolean isConfirmed() {
        return confirmed;
    }

    public Long getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Long initiatorId) {
        this.initiatorId = initiatorId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getInitiatorGroupId() {
        return initiatorGroupId;
    }

    public void setInitiatorGroupId(Long initiatorGroupId) {
        this.initiatorGroupId = initiatorGroupId;
    }

    public Long getReceiverGroupId() {
        return receiverGroupId;
    }

    public void setReceiverGroupId(Long receiverGroupId) {
        this.receiverGroupId = receiverGroupId;
    }

    public Connection toBean(ConnectionDto dto) {
        Connection c = new Connection();
        c.setConfirmed(dto.getConfirmed());
        return c;
    }
}
