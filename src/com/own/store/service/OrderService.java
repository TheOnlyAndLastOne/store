package com.own.store.service;

import com.own.store.domain.Order;
import com.own.store.domain.PageModel;
import com.own.store.domain.User;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/13 0013 11:53
 * @Description:
 */
public interface OrderService {
    void saveOrder(Order order) throws Exception;

    PageModel findMyOrdersWithPage(User user, int num) throws Exception;

    Order findOrderByOid(String oid) throws Exception;

    void updateOrder(Order order) throws Exception;
}
