package com.own.store.dao.daoImpl;

import com.own.store.dao.CategoryDao;
import com.own.store.domain.Category;
import com.own.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/10 0010 9:24
 * @Description:
 */
public class CategoryDaoImpl implements CategoryDao {
    @Override
    public List<Category> getAllCats() throws SQLException {
        String sql = "select * from category";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        return qr.query(sql,new BeanListHandler<>(Category.class));
    }
}
