package com.parking.dao.vehicle;

import com.parking.dao.JpaDao;
import com.parking.entity.Vehicle;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class JpaVehicleDao extends JpaDao<Vehicle, Long> implements VehicleDao {

    public JpaVehicleDao() {
        super(Vehicle.class);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> findAll() {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Vehicle> criteriaQuery = builder.createQuery(Vehicle.class);

        Root<Vehicle> root = criteriaQuery.from(Vehicle.class);
        criteriaQuery.orderBy(builder.desc(root.get("date")));

        TypedQuery<Vehicle> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

}
