package net.stevencai.dao;

import net.stevencai.entity.Account;
import net.stevencai.entity.User;

import java.util.List;

public interface AccountDAO {

    List<Account> getAccounts(User user);

    void saveAccount(Account account);

    Account getAccount(int accountId);

    List<Account> getAccount(String title, User user);

    void deleteAccount(int accountId);

    void deleteAccount(String accountName);

    void deleteAccount(Account account);
}
