package com.parking.model.dao.jpa;

import com.parking.entity.Account;
import com.parking.entity.Parking;
import com.parking.model.JpaDao;
import com.parking.model.dao.ParkingDao;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class JpaParkingDao extends JpaDao<Parking, Long> implements ParkingDao {

    public JpaParkingDao() {
        super(Parking.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Parking> findAll() {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Parking> criteriaQuery = builder.createQuery(Parking.class);

        Root<Parking> root = criteriaQuery.from(Parking.class);
        criteriaQuery.orderBy(builder.desc(root.get("date")));

        TypedQuery<Parking> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Parking> findParkingsByAccount(Long accountId) {
        Query query = getEntityManager().createQuery("SELECT a FROM Parking a WHERE a.account.id=?1");
        query.setParameter(1, accountId);
        return query.getResultList();
    }

    public List<Parking> findParkingsByAccountName(String name) {
        Query query = getEntityManager().createQuery("SELECT a FROM Parking a WHERE a.account.name=?1");
        query.setParameter(1, name);
        return query.getResultList();
    }

    public List<Parking> findParkingsByAccount(Account loggedAccount) {
        Query query = getEntityManager().createQuery("SELECT a FROM Parking a WHERE a.account.name=?1");
        query.setParameter(1, loggedAccount.getName());
        return query.getResultList();
    }

    @Override
    public Parking save(Account loggedAccount, Parking parking) {
        parking.setAccount(loggedAccount);
        return save(parking);
    }

}
