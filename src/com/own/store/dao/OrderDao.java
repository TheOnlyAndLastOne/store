package com.own.store.dao;

import com.own.store.domain.Order;
import com.own.store.domain.OrderItem;
import com.own.store.domain.User;

import java.sql.Connection;
import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/13 0013 11:53
 * @Description:
 */
public interface OrderDao {
    void saveOrder(Connection conn, Order order) throws Exception;

    void saveOrderItem(Connection conn, OrderItem item) throws Exception;

    int getTotalRecords(User user) throws Exception;

    List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception;

    Order findOrderByOid(String oid) throws Exception;

    void updateOrder(Order order) throws Exception;
}
