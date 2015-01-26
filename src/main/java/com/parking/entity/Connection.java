package com.parking.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@javax.persistence.Entity
public class Connection implements Entity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Account initiator;

    @OneToOne
    private Account receiver;

    @OneToOne
    private UserGroup initiatorGroup;

    @OneToOne
    private UserGroup receiverGroup;

    Date created;

    private Boolean confirmed;

    public Connection() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getInitiator() {
        return initiator;
    }

    public void setInitiator(Account initiator) {
        this.initiator = initiator;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public UserGroup getInitiatorGroup() {
        return initiatorGroup;
    }

    public void setInitiatorGroup(UserGroup initiatorGroup) {
        this.initiatorGroup = initiatorGroup;
    }

    public UserGroup getReceiverGroup() {
        return receiverGroup;
    }

    public void setReceiverGroup(UserGroup receiverGroup) {
        this.receiverGroup = receiverGroup;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection)) return false;

        Connection that = (Connection) o;

        if (!confirmed.equals(that.confirmed)) return false;
        if (!id.equals(that.id)) return false;
        if (!initiator.equals(that.initiator)) return false;
        if (!initiatorGroup.equals(that.initiatorGroup)) return false;
        if (!receiver.equals(that.receiver)) return false;
        if (!receiverGroup.equals(that.receiverGroup)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + initiator.hashCode();
        result = 31 * result + receiver.hashCode();
        result = 31 * result + initiatorGroup.hashCode();
        result = 31 * result + receiverGroup.hashCode();
        result = 31 * result + confirmed.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "id=" + id +
                ", initiator=" + initiator +
                ", receiver=" + receiver +
                ", initiatorGroup=" + initiatorGroup +
                ", receiverGroup=" + receiverGroup +
                ", confirmed=" + confirmed +
                '}';
    }
}
