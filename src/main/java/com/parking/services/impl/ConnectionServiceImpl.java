package com.parking.services.impl;

import com.parking.dao.connection.ConnectionDao;
import com.parking.entity.Connection;
import com.parking.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    private ConnectionDao connectionDao;

    @Override
    public List<Connection> findAllConnections() {
        return connectionDao.findAll();
    }

    @Override
    public Connection findConnection(Long id) {
        return connectionDao.find(id);
    }

    @Override
    public List<Connection> findConnectionsByAccountName(String name) {
        return connectionDao.findConnectionsByAccountName(name);
    }

    @Override
    public Connection findByInitiatorReceiver(Long initiatorId, Long receiverId) {
        return connectionDao.findByInitiatorReceiver(initiatorId, receiverId);
    }

    @Override
    public List<Connection> findConnectionsByAccountId(Long initiatorId) {
        return connectionDao.findConnectionsByAccountId(initiatorId);
    }

    @Override
    public Connection findConnectionByAccountNames(String initiatorName, String receiverName) {
        return connectionDao.findConnectionByAccountNames(initiatorName, receiverName);
    }

}
