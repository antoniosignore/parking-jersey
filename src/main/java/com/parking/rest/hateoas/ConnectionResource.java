package com.parking.rest.hateoas;

import com.parking.entity.Connection;
import org.springframework.hateoas.ResourceSupport;

public class ConnectionResource extends ResourceSupport {

    private Boolean confirmed;

    private Long rid;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Connection toConnection() {
        Connection connection = new Connection();
        connection.setConfirmed(confirmed);
        return connection;
    }
}
