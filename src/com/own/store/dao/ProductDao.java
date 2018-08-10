package com.own.store.dao;

import com.own.store.domain.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/10 0010 10:57
 * @Description:
 */
public interface ProductDao {
    List<Product> findHots() throws SQLException;

    List<Product> findNews() throws SQLException;

    Product findProductByPid(String pid) throws SQLException;

    int findProductTotal(String cid) throws SQLException;

    List findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws SQLException;
}
