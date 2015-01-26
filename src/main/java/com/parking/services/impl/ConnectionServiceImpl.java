package com.parking.services.impl;

import com.parking.core.models.entities.Connection;
import com.parking.core.repositories.ConnectionRepo;
import com.parking.core.services.ConnectionService;
import com.parking.core.services.util.ConnectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    private ConnectionRepo connectionRepo;

    @Override
    public List<Connection> findConnectionsByAccountId(Long id) {
        return connectionRepo.findConnectionsByAccountId(id);
    }

    @Override
    public ConnectionList findAllConnections() {
        return new ConnectionList(connectionRepo.findAllConnections());
    }

    @Override
    public Connection findConnection(Long id) {
        return connectionRepo.findConnection(id);
    }

    @Override
    public List<Connection> findConnectionsByAccountName(String name) {
        return connectionRepo.findConnectionsByAccountName(name);
    }
}
