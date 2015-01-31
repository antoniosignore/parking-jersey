package com.parking.dao.connection;

import com.parking.dao.JpaDao;
import com.parking.entity.Connection;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class JpaConnectionDao extends JpaDao<Connection, Long> implements ConnectionDao {

    public JpaConnectionDao() {
        super(Connection.class);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Connection> findAll() {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Connection> criteriaQuery = builder.createQuery(Connection.class);

        Root<Connection> root = criteriaQuery.from(Connection.class);
        criteriaQuery.orderBy(builder.desc(root.get("date")));

        TypedQuery<Connection> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Connection> findConnectionsByAccountName(String name) {
        Query query = getEntityManager().createQuery("SELECT b from Connection b where b.initiator.name=?1");
        query.setParameter(1, name);
        return query.getResultList();
    }

    @Override
    public Connection findByInitiatorReceiver(Long initiatorId, Long receiverId) {
        Query query = getEntityManager().createQuery("SELECT b from Connection b where b.initiator.id=?1 and b.receiver.id=?2");
        query.setParameter(1, initiatorId);
        query.setParameter(2, receiverId);
        return (Connection) query.getSingleResult();
    }

    @Override
    public List<Connection> findConnectionsByAccountId(Long accountId) {
        Query query = getEntityManager().createQuery("SELECT a FROM Connection a WHERE a.initiator.id=?1");
        query.setParameter(1, accountId);
        return query.getResultList();
    }

    @Override
    public Connection findConnectionByAccountNames(String initiatorName, String receiverName) {
        Query query = getEntityManager().createQuery("SELECT a FROM Connection a WHERE a.initiator.name=?1 AND a.initiator.reveiver.name=?2");
        query.setParameter(1, initiatorName);
        query.setParameter(2, receiverName);
        return (Connection) query.getSingleResult();
    }
}
