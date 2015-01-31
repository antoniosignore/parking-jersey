package com.parking.dao.connection;

import com.parking.dao.Dao;
import com.parking.entity.Connection;

import java.util.List;


public interface ConnectionDao extends Dao<Connection, Long> {

    List<Connection> findConnectionsByAccountName(String name);

    List<Connection> findConnectionsByAccountId(Long id);

    Connection findByInitiatorReceiver(Long initiatorId, Long receiverId);

    Connection findConnectionByAccountNames(String initiatorName, String receiverName);
}