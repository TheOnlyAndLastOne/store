package com.own.store.dao;

import com.own.store.domain.Category;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/10 0010 9:24
 * @Description:
 */
public interface CategoryDao {
    List<Category> getAllCats() throws SQLException;
}
