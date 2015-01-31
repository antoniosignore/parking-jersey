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
    private AccountGroup initiatorGroup;

    @OneToOne
    private AccountGroup receiverGroup;

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

    public AccountGroup getInitiatorGroup() {
        return initiatorGroup;
    }

    public void setInitiatorGroup(AccountGroup initiatorGroup) {
        this.initiatorGroup = initiatorGroup;
    }

    public AccountGroup getReceiverGroup() {
        return receiverGroup;
    }

    public void setReceiverGroup(AccountGroup receiverGroup) {
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
        if (!receiver.equals(that.receiver)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + initiator.hashCode();
        result = 31 * result + receiver.hashCode();
        result = 31 * result + initiatorGroup.hashCode();
        result = 31 * result + receiverGroup.hashCode();
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
