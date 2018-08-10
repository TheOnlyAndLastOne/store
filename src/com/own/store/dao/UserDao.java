package com.own.store.dao;

import com.own.store.domain.User;

import java.sql.SQLException;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/8 0008 17:16
 * @Description:
 */
public interface UserDao {
    void userRegister(User user) throws SQLException;

    User userActive(String code) throws SQLException;

    void updateUser(User user) throws SQLException;

    User userLogin(User user) throws SQLException;
}
