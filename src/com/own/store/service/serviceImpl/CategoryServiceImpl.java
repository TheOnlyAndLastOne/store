package com.own.store.service.serviceImpl;

import com.own.store.dao.CategoryDao;
import com.own.store.dao.daoImpl.CategoryDaoImpl;
import com.own.store.domain.Category;
import com.own.store.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/10 0010 9:23
 * @Description:
 */
public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<Category> getAllCats() throws SQLException {
        CategoryDao categoryDao = new CategoryDaoImpl();
        return categoryDao.getAllCats();
    }
}
