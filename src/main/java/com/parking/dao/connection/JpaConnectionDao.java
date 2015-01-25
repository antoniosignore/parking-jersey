package com.parking.dao.connection;

import com.parking.entity.Connection;
import com.parking.dao.JpaDao;
import org.springframework.transaction.annotation.Transactional;

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
//        criteriaQuery.orderBy(builder.desc(root.get("date")));

        TypedQuery<Connection> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

}
