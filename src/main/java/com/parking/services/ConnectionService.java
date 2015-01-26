package com.parking.services;


import com.parking.entity.Connection;
import com.parking.services.util.ConnectionList;

import java.util.List;

public interface ConnectionService {

    public ConnectionList findAllConnections();

    public Connection findConnection(Long id);

    public List<Connection> findConnectionsByAccountName(String name);

    public List<Connection> findConnectionsByAccountId(Long id);
}
