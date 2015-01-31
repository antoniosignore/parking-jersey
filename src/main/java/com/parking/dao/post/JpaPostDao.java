package com.parking.dao.post;

import com.parking.dao.JpaDao;
import com.parking.entity.Account;
import com.parking.entity.Post;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
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

    @Override
    public Post findPostByTitle(String title) {
        Query query = getEntityManager().createQuery("SELECT b from Post b where b.title=?1");
        query.setParameter(1, title);
        List<Post> blogs = query.getResultList();
        if (blogs.isEmpty()) {
            return null;
        } else {
            return blogs.get(0);
        }
    }

    @Override
    public List<Post> findBlogsByAccount(Account accountId) {
        Query query = getEntityManager().createQuery("SELECT b from Post b where b.owner.id=?1");
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
