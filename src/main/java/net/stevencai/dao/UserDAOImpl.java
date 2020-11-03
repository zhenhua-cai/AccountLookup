package net.stevencai.dao;

import net.stevencai.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUser(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User u where u.username=:username", User.class);
        query.setParameter("username", username);
        try {
            return query.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void saveUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
    }

    @Override
    public User getUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }
}
