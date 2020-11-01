package net.stevencai.service;

import net.stevencai.entity.Account;

import java.util.List;

public interface LookupService {
    List<Account> getAccount(String title);
    void saveAccount(Account account);
    void deleteAccount(int accountId);
    void deleteAccount(String accountName);
    List<Account> getAccounts();
}
