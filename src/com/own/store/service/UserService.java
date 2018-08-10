package com.own.store.service;

import com.own.store.domain.User;

import java.sql.SQLException;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/8 0008 17:19
 * @Description:
 */
public interface UserService {
    //注册功能
    void userRegister(User user) throws SQLException;

    boolean userActive(String code) throws SQLException;

    User userLogin(User user) throws SQLException;
}
