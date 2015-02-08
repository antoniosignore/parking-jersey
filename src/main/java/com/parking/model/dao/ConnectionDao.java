package com.parking.model.dao;

import com.parking.entity.Account;
import com.parking.entity.Connection;
import com.parking.model.Dao;

import java.util.List;


public interface ConnectionDao extends Dao<Connection, Long> {

    List<Connection> findConnectionsByAccountName(String name);

    List<Connection> findConnectionsByAccountId(Long id);

    Connection findByInitiatorIdReceiverId(Long initiatorId, Long receiverId);

    Connection findConnectionByAccountNames(String initiatorName, String receiverName);

    List<Connection> findByInitiator(Account initiator);

}