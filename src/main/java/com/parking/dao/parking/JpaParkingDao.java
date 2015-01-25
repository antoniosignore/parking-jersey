package com.parking.dao.parking;

import com.parking.dao.JpaDao;
import com.parking.entity.Parking;
import org.springframework.transaction.annotation.Transactional;

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

}
