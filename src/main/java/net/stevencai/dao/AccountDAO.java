package net.stevencai.dao;

import net.stevencai.entity.Account;

import java.util.List;

public interface AccountDAO {

    List<Account> getAccounts();
    void saveAccount(Account account);

    Account getAccount(int accountId);

    List<Account> getAccount(String title);

    void deleteAccount(int accountId);

    void deleteAccount(String accountName);
}
