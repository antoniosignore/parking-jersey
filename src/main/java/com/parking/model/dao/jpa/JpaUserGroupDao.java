package com.parking.model.dao.jpa;

import com.parking.model.JpaDao;
import com.parking.entity.Account;
import com.parking.entity.AccountGroup;
import com.parking.model.dao.UserGroupDao;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class JpaUserGroupDao extends JpaDao<AccountGroup, Long> implements UserGroupDao {

    public JpaUserGroupDao() {
        super(AccountGroup.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountGroup> findAll() {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<AccountGroup> criteriaQuery = builder.createQuery(AccountGroup.class);

        Root<AccountGroup> root = criteriaQuery.from(AccountGroup.class);
        criteriaQuery.orderBy(builder.desc(root.get("groupName")));

        TypedQuery<AccountGroup> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<AccountGroup> findByUserId(Long userId) {
        Query query = getEntityManager().createQuery("SELECT a FROM AccountGroup a WHERE a.account.id=?1");
        query.setParameter(1, userId);
        return query.getResultList();
    }

    @Override
    public List<AccountGroup> findByUser(Account loggedAccount) {
        Query query = getEntityManager().createQuery("SELECT a FROM AccountGroup a WHERE a.account.id=?1");
        query.setParameter(1, loggedAccount.getId());
        return query.getResultList();
    }
}
