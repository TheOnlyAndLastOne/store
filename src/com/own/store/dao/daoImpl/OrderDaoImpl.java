package com.own.store.dao.daoImpl;

import com.own.store.dao.OrderDao;
import com.own.store.domain.Order;
import com.own.store.domain.OrderItem;
import com.own.store.domain.Product;
import com.own.store.domain.User;
import com.own.store.utils.JDBCUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/13 0013 11:54
 * @Description:
 */
public class OrderDaoImpl implements OrderDao {
    @Override
    public void saveOrder(Connection conn, Order order) throws Exception {
        String sql = "insert into orders values (?,?,?,?,?,?,?,?)";
        QueryRunner qr = new QueryRunner();
        Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
        qr.update(conn,sql,params);
    }

    @Override
    public void saveOrderItem(Connection conn, OrderItem item) throws Exception {
        String sql = "insert into orderitem values (?,?,?,?,?)";
        QueryRunner qr = new QueryRunner();
        Object[] params = {item.getItemid(),item.getQuantity(),item.getTotal(),item.getProduct().getPid(),item.getOrder().getOid()};
        qr.update(conn,sql,params);
    }

    @Override
    public int getTotalRecords(User user) throws Exception {
        String sql = "select count(*) from orders where uid = ?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        Long num = (Long)qr.query(sql,new ScalarHandler(),user.getUid());
        return num.intValue();
    }

    @Override
    public List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception {
        String sql = "select * from orders where uid = ? order by ordertime desc limit ?,?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        List<Order> list = qr.query(sql,new BeanListHandler<>(Order.class),user.getUid(),startIndex,pageSize);
        //遍历所有订单
        for(Order order : list){
            //查询每笔订单下的订单项和商品信息
            //select * from orderitem o,product p where o.pid = p.pid and oid = ?
            String oid = order.getOid();
            sql = "select * from orderitem o,product p where o.pid = p.pid and oid = ?";
            List<Map<String,Object>> list02 = qr.query(sql,new MapListHandler(),oid);
            for(Map<String,Object> map : list02){
                OrderItem orderItem = new OrderItem();
                Product product = new Product();

                // 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
                // 1_创建时间类型的转换器
                DateConverter dt = new DateConverter();
                // 2_设置转换的格式
                dt.setPattern("yyyy-MM-dd");
                // 3_注册转换器
                ConvertUtils.register(dt, java.util.Date.class);

                //将map中的数据填充到对象上
                BeanUtils.populate(orderItem,map);
                BeanUtils.populate(product,map);
                //商品绑定到订单项
                orderItem.setProduct(product);
                //订单项绑定到订单
                order.getList().add(orderItem);
            }
        }
        return list;
    }

    @Override
    public Order findOrderByOid(String oid) throws Exception {
        String sql = "select * from orders where oid = ?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        Order order = qr.query(sql,new BeanHandler<>(Order.class),oid);
        //查询订单下的订单项以及商品信息


        sql = "select * from orderitem o,product p where o.pid = p.pid and oid = ?";
        List<Map<String,Object>> list02 = qr.query(sql,new MapListHandler(),oid);
        for(Map<String,Object> map : list02){
            OrderItem orderItem = new OrderItem();
            Product product = new Product();

            // 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
            // 1_创建时间类型的转换器
            DateConverter dt = new DateConverter();
            // 2_设置转换的格式
            dt.setPattern("yyyy-MM-dd");
            // 3_注册转换器
            ConvertUtils.register(dt, java.util.Date.class);

            //将map中的数据填充到对象上
            BeanUtils.populate(orderItem,map);
            BeanUtils.populate(product,map);
            //商品绑定到订单项
            orderItem.setProduct(product);
            //订单项绑定到订单
            order.getList().add(orderItem);
        }

        return order;
    }

    @Override
    public void updateOrder(Order order) throws Exception {

    }
}
