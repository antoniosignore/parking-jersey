package com.parking.dao.accountGroup;

import com.parking.dao.JpaDao;
import com.parking.entity.UserGroup;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class JpaUserGroupDao extends JpaDao<UserGroup, Long> implements UserGroupDao {

    public JpaUserGroupDao() {
        super(UserGroup.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserGroup> findAll() {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<UserGroup> criteriaQuery = builder.createQuery(UserGroup.class);

        Root<UserGroup> root = criteriaQuery.from(UserGroup.class);
        criteriaQuery.orderBy(builder.desc(root.get("groupName")));

        TypedQuery<UserGroup> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }


    @Override
    public List<UserGroup> findByUserId(Long userId) {

        Query query = getEntityManager().createQuery("SELECT a FROM UserGroup a WHERE a.account.id=?1");
        query.setParameter(1, userId);
        return query.getResultList();

    }
}
