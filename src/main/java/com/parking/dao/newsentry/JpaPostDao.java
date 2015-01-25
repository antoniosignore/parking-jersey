package com.parking.dao.newsentry;

import com.parking.dao.JpaDao;
import com.parking.entity.Post;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class JpaPostDao extends JpaDao<Post, Long> implements PostDao {

    public JpaPostDao() {
        super(Post.class);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Post> findAll() {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Post> criteriaQuery = builder.createQuery(Post.class);

        Root<Post> root = criteriaQuery.from(Post.class);
        criteriaQuery.orderBy(builder.desc(root.get("date")));

        TypedQuery<Post> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

}
