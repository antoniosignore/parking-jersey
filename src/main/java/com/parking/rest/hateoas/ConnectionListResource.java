package com.parking.rest.hateoas;

import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class ConnectionListResource extends ResourceSupport {

    private List<ConnectionResource> connections = new ArrayList<ConnectionResource>();

    public List<ConnectionResource> getConnections() {
        return connections;
    }

    public void setConnections(List<ConnectionResource> connections) {
        this.connections = connections;
    }

}
