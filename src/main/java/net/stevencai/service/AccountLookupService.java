package net.stevencai.service;

import net.stevencai.dao.AccountDAO;
import net.stevencai.dao.UserDAO;
import net.stevencai.entity.Account;
import net.stevencai.entity.User;
import net.stevencai.security.LoginVerify;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountLookupService implements LookupService {

    private AccountDAO accountDAO;
    @Autowired
    private UserDAO userDAO;

    @Autowired
    public AccountLookupService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Autowired
    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Transactional
    @Override
    public List<Account> getAccount(String title, User user) {
        return accountDAO.getAccount(title, user);
    }

    @Transactional
    @Override
    public void saveAccount(Account account) {
        accountDAO.saveAccount(account);
    }

    @Transactional
    @Override
    public void deleteAccount(int accountId) {
        accountDAO.deleteAccount(accountId);
    }

    @Override
    public void deleteAccount(String accountName) {
        accountDAO.deleteAccount(accountName);
    }

    @Transactional
    @Override
    public List<Account> getAccounts(User user) {
        return accountDAO.getAccounts(user);
    }

    @Transactional
    @Override
    public void deleteAccount(Account account) {
        accountDAO.deleteAccount(account);
    }

    @Transactional
    @Override
    public User getUser(String username) {
        return userDAO.getUser(username);
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    @Transactional
    @Override
    public User getUser(int id) {
        return userDAO.getUser(id);
    }

    @Override
    public boolean comparePassword(String passwordPlainText, String passwordInDB) {
        return LoginVerify.checkPassword(passwordPlainText, passwordInDB);
    }
}
