package net.stevencai.dao;

import net.stevencai.entity.User;

public interface UserDAO {
    User getUser(String username);

    void saveUser(User user);

    User getUser(int id);
}
