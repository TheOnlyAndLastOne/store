package com.own.store.service.serviceImpl;

import com.own.store.dao.UserDao;
import com.own.store.dao.daoImpl.UserDaoImpl;
import com.own.store.domain.User;
import com.own.store.service.UserService;

import java.sql.SQLException;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/8 0008 17:19
 * @Description:
 */
public class UserServiceImpl implements UserService {
    //注册
    @Override
    public void userRegister(User user) throws SQLException {
        //注册
        UserDao userDao = new UserDaoImpl();
        userDao.userRegister(user);
    }

    //激活
    @Override
    public boolean userActive(String code) throws SQLException {

        UserDao userDao = new UserDaoImpl();
        User user = userDao.userActive(code);

        if(null != user){
            //修改状态，删除code
            user.setState(1);
            user.setCode(null);
            userDao.updateUser(user);
            return true;
        }else{
            return false;
        }
    }

    //登陆
    @Override
    public User userLogin(User user) throws SQLException {
        UserDao userDao = new UserDaoImpl();
        User uu = userDao.userLogin(user);
        if(null == uu){
            throw new RuntimeException("密码不正确");
        }else if(uu.getState() == 0){
            throw new RuntimeException("用户未激活");
        }else{
            return uu;
        }
    }
}
