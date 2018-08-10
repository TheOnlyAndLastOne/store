package com.own.store.service;

import com.own.store.domain.PageModel;
import com.own.store.domain.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/10 0010 10:56
 * @Description:
 */
public interface ProductService {
    List<Product> findHots() throws SQLException;

    List<Product> findNews() throws SQLException;

    Product findProductByPid(String pid) throws SQLException;

    PageModel findProductsByCidWithPage(String cid, int num) throws SQLException;
}
