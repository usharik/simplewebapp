package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public void delete(User user) {
        logger.info("Deleting user");

        try {
            User attached = findById(user.getId());
            if (attached != null) {
                em.remove(attached);
            }
        } catch (Exception ex) {
            logger.error("Error with entity class" , ex);
            throw new IllegalStateException(ex);
        }
    }

    public User findById(int id) {
        return em.find(User.class, id);
    }

    public boolean existsById(int id) {
        return findById(id) != null;
    }

    public List<User> getAllUsers() {
        return em.createQuery("from User ").getResultList();
    }
}
