package net.stevencai.service;

import net.stevencai.entity.Account;
import net.stevencai.entity.User;

import java.util.List;

public interface LookupService {
    List<Account> getAccount(String title, User user);
    void saveAccount(Account account);
    void deleteAccount(int accountId);
    void deleteAccount(String accountName);
    List<Account> getAccounts(User user);

    void deleteAccount(Account account);
    User getUser(String username);
    void saveUser(User user);
    User getUser(int id);
    boolean comparePassword(String passwordPlainText, String passwordInDB);
}
