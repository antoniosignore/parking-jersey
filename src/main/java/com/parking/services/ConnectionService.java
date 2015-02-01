package com.parking.services;


import com.parking.entity.Account;
import com.parking.entity.Connection;

import java.util.List;

public interface ConnectionService {

    public List<Connection> findAllConnections();

    public Connection findConnection(Long id);

    public List<Connection> findConnectionsByAccountName(String name);

    public List<Connection> findConnectionsByAccountId(Long id);

    public Connection findByInitiatorReceiver(Long initiatorId, Long receiverId);

    public Connection findConnectionByAccountNames(String initiatorName, String receiverName);

    List<Connection> findAllConnectionByAccount(Account loggedAccount);

    Connection update(Long id, Connection connection);

    Connection createConnection(Account loggedAccount, Connection connection);

    void delete(Long id);
}
