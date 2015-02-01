package com.parking.model.dao.jpa;

import com.parking.model.JpaDao;
import com.parking.entity.Account;
import com.parking.model.dao.AccountDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;


public class JpaAccountDao extends JpaDao<Account, Long> implements AccountDao {

    public JpaAccountDao() {
        super(Account.class);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.findByName(username);
        if (null == account) {
            throw new UsernameNotFoundException("The user with name " + username + " was not found");
        }

        return account;
    }


    @Override
    @Transactional(readOnly = true)
    public Account findByName(String name) {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Account> criteriaQuery = builder.createQuery(this.entityClass);

        Root<Account> root = criteriaQuery.from(this.entityClass);
        Path<String> namePath = root.get("name");
        criteriaQuery.where(builder.equal(namePath, name));

        TypedQuery<Account> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        List<Account> accounts = typedQuery.getResultList();

        if (accounts.isEmpty()) {
            return null;
        }

        return accounts.iterator().next();
    }

}
