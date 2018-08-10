package com.own.store.service;

import com.own.store.domain.Category;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/10 0010 9:23
 * @Description:
 */
public interface CategoryService {
    List<Category> getAllCats() throws SQLException;
}
