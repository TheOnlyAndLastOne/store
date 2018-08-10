package com.own.store.dao.daoImpl;

import com.own.store.dao.UserDao;
import com.own.store.domain.User;
import com.own.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/8 0008 17:16
 * @Description:
 */
public class UserDaoImpl implements UserDao {
    //注册
    @Override
    public void userRegister(User user) throws SQLException {
        String sql = "insert into user values (?,?,?,?,?,?,?,?,?,?) ";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),user.getName(),
                user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
        qr.update(sql,params);
    }

    //激活
    @Override
    public User userActive(String code) throws SQLException {
        String sql = "select * from user where code = ?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        User user = qr.query(sql,new BeanHandler<User>(User.class),code);
        return user;
    }

    //更新用户状态和清空code
    @Override
    public void updateUser(User user) throws SQLException {
        String sql = "update user set uid=?,username=?,password=?,name=?,email=?,telephone=?,birthday=?,sex=?,state=? where code=?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),user.getName(),
                user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
        qr.update(sql,params);
    }

    //登陆
    @Override
    public User userLogin(User user) throws SQLException {
        String sql = "select * from user where username = ? and password = ?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        User uu = qr.query(sql,new BeanHandler<>(User.class),user.getUsername(),user.getPassword());
        return uu;
    }
}
