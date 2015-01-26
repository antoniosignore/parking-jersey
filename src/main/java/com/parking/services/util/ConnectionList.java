package com.parking.services.util;


import com.parking.entity.Connection;

import java.util.ArrayList;
import java.util.List;

public class ConnectionList {

    private List<Connection> connections = new ArrayList<Connection>();

    public ConnectionList(List resultList) {
        this.connections = resultList;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}
