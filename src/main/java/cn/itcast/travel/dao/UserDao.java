package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * Find a user in the database
     * @param user
     * @return
     */
    User FindUser(User user);

    /**
     * Save user into the database
     * @param user
     */
    void SaveUser(User user);
    User findUserByCode(String code);

    boolean setUserStatus(User user);

    User findUserByUsername(String username);
}
