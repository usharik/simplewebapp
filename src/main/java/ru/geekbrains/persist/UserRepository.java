package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Named
public class UserRepository {

    private Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    public UserRepository() {
    }

    @Transactional
    public User merge(User user) {
        return em.merge(user);
    }

    @Transactional
    public void delete(int id) {
        logger.info("Deleting user");

        try {
            User attached = findById(id);
            if (attached != null) {
                em.remove(attached);
            }
        } catch (Exception ex) {
            logger.error("Error with entity class" , ex);
            throw new IllegalStateException(ex);
        }
    }

    @Transactional
    public User findById(int id) {
        return em.find(User.class, id);
    }

    @Transactional
    public boolean existsById(int id) {
        return findById(id) != null;
    }

    @Transactional
    public List<User> getAllUsers() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> from = query.from(User.class);
        from.fetch("roles", JoinType.LEFT);
        query.select(from).distinct(true);

        return em.createQuery(query).getResultList();

//        return em.createQuery("select distinct u from User u left join fetch u.roles", User.class)
//                .getResultList();
    }
}
