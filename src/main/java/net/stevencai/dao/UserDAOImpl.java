package net.stevencai.dao;

import net.stevencai.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUser(String username) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, username);
    }
}
