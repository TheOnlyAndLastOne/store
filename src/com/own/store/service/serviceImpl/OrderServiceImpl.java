package com.own.store.service.serviceImpl;

import com.own.store.dao.OrderDao;
import com.own.store.dao.daoImpl.OrderDaoImpl;
import com.own.store.domain.Order;
import com.own.store.domain.OrderItem;
import com.own.store.domain.PageModel;
import com.own.store.domain.User;
import com.own.store.service.OrderService;
import com.own.store.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/13 0013 11:53
 * @Description:
 */
public class OrderServiceImpl implements OrderService {
    OrderDao orderDao =  new OrderDaoImpl();

    @Override
    public void saveOrder(Order order) throws Exception {
        //保存订单和订单下所有的订单项，同时成功或者失败
        /*try {
            JDBCUtils.startTransaction();
            OrderDao orderDao =  new OrderDaoImpl();
            orderDao.saveOrder(order);
            for(OrderItem orderItem : order.getList()){
                orderDao.saveOrderItem(orderItem);
            }
            JDBCUtils.commitAndClose();
        } catch (SQLException e) {
            JDBCUtils.rollbackAndClose();
        }*/
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            //开启事务
            conn.setAutoCommit(false);
            //保存订单
            orderDao.saveOrder(conn,order);
            //保存订单项
            for(OrderItem item : order.getList()){
                orderDao.saveOrderItem(conn,item);
            }
            conn.commit();
        }catch (Exception e){
            conn.rollback();
        }
    }

    @Override
    public PageModel findMyOrdersWithPage(User user, int num) throws Exception {
        //1.创建pageModel对象
        //select count(*) from orders where uid=?
        int total = orderDao.getTotalRecords(user);
        PageModel pm = new PageModel(num,total,3);
        //2.关联集合
        //select count(*) from orders where uid=? limit ?,?
        List list = orderDao.findMyOrdersWithPage(user,pm.getStartIndex(),pm.getPageSize());
        pm.setList(list);
        //3.关联url
        pm.setUrl("OrderServlet?method=findMyOrdersWithPage");
        return pm;
    }

    @Override
    public Order findOrderByOid(String oid) throws Exception {
        return orderDao.findOrderByOid(oid);
    }

    @Override
    public void updateOrder(Order order) throws Exception {
        orderDao.updateOrder(order);
    }
}
