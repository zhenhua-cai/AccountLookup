package net.stevencai.service;

import net.stevencai.entity.Account;
import net.stevencai.entity.User;

import java.util.List;

public interface LookupService {
    List<Account> getAccount(String title);
    void saveAccount(Account account);
    void deleteAccount(int accountId);
    void deleteAccount(String accountName);
    List<Account> getAccounts();

    void deleteAccount(Account account);
    User getUser(String username);
    boolean comparePassword(String passwordPlainText, String passwordInDB);
}
