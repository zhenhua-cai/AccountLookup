package net.stevencai.dao;

import net.stevencai.entity.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDAOImpl implements AccountDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Account> getAccounts() {
        Session session = sessionFactory.getCurrentSession();
        Query<Account> query = session.createQuery("from Account",Account.class);
        return query.getResultList();
    }

    @Override
    public void saveAccount(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(account);
    }

    @Override
    public Account getAccount(int accountId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Account.class,accountId);
    }

    @Override
    public List<Account> getAccount(String title) {
        Session session = sessionFactory.getCurrentSession();
        Query<Account> query = session.createQuery("from Account a where a.title=:title",Account.class);
        query.setParameter("title",title);
        return query.getResultList();
    }

    @Override
    public void deleteAccount(int accountId) {
        Session session = sessionFactory.getCurrentSession();
        Query<?> query = session.createQuery("delete from Account a where a.title=:id",Account.class);
        query.setParameter("id",accountId);
        query.executeUpdate();
    }

    @Override
    public void deleteAccount(String accountName) {
        Session session = sessionFactory.getCurrentSession();
        Query<?> query = session.createQuery("delete from Account a where a.title=:accountName",Account.class);
        query.setParameter("accountName",accountName);
        query.executeUpdate();
    }

    @Override
    public void deleteAccount(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(account);
    }
}
