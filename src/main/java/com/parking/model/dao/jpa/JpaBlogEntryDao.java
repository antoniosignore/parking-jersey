package com.parking.model.dao.jpa;

import com.parking.entity.Account;
import com.parking.entity.BlogEntry;
import com.parking.model.JpaDao;
import com.parking.model.dao.BlogEntryDao;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class JpaBlogEntryDao extends JpaDao<BlogEntry, Long> implements BlogEntryDao {

    public JpaBlogEntryDao() {
        super(BlogEntry.class);
    }


    @Override
    @Transactional(readOnly = true)
    public List<BlogEntry> findAll() {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<BlogEntry> criteriaQuery = builder.createQuery(BlogEntry.class);

        Root<BlogEntry> root = criteriaQuery.from(BlogEntry.class);
        criteriaQuery.orderBy(builder.desc(root.get("date")));

        TypedQuery<BlogEntry> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public BlogEntry findPostByTitle(String title) {
        Query query = getEntityManager().createQuery("SELECT b from BlogEntry b where b.title=?1");
        query.setParameter(1, title);
        List<BlogEntry> blogs = query.getResultList();
        if (blogs.isEmpty()) {
            return null;
        } else {
            return blogs.get(0);
        }
    }

    @Override
    public List<BlogEntry> findBlogsByAccount(Account accountId) {
        Query query = getEntityManager().createQuery("SELECT b from BlogEntry b where b.owner.id=?1");
        query.setParameter(1, accountId.getId());
        return query.getResultList();
    }

//    @Override
//    public List<Post> findAllRepliesByPostId(Long postId) {
//        Query query = getEntityManager().createQuery("SELECT b from Post b where b.replyToPostId=?1");
//        query.setParameter(1, postId);
//        return query.getResultList();
//
//    }
}
