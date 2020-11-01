package net.stevencai.service;

import net.stevencai.dao.AccountDAO;
import net.stevencai.entity.Account;
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
    public AccountLookupService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Autowired
    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Transactional
    @Override
    public List<Account> getAccount(String title){
        return accountDAO.getAccount(title);
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
    public List<Account> getAccounts() {
        return accountDAO.getAccounts();
    }

    @Transactional
    @Override
    public void deleteAccount(Account account) {
        accountDAO.deleteAccount(account);
    }
}
