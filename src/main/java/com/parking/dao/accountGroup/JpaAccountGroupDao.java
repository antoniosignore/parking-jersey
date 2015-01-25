package com.parking.dao.accountGroup;

import com.parking.dao.JpaDao;
import com.parking.entity.AccountGroup;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class JpaAccountGroupDao extends JpaDao<AccountGroup, Long> implements AccountGroupDao {

    public JpaAccountGroupDao() {
        super(AccountGroup.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountGroup> findAll() {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<AccountGroup> criteriaQuery = builder.createQuery(AccountGroup.class);

        Root<AccountGroup> root = criteriaQuery.from(AccountGroup.class);
//        criteriaQuery.orderBy(builder.desc(root.get("date")));

        TypedQuery<AccountGroup> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

}
