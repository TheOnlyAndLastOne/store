package com.own.store.test;

import com.own.store.domain.OrderItem;
import com.own.store.domain.Product;
import com.own.store.utils.JDBCUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/13 0013 15:02
 * @Description:
 */
public class TestMapListHandler {
    //根据订单id查询订单下所有的订单项
    //select * from orderitem o,product p where o.pid = p.pid and oid = ?
    @Test
    public void test00() throws Exception {
        String sql = "select * from orderitem o,product p where o.pid = p.pid and oid = '843B27021538415AB34BD4DC7DF19B7F' ";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        //由于返回的数据是来自多个表，多行数据MapListHandler返回数据
        List<Map<String,Object>> list = qr.query(sql,new MapListHandler());
        for(Map<String,Object> map : list){
            for(Map.Entry<String, Object> entry : map.entrySet()){
                System.out.print(entry.getKey()+":"+entry.getValue()+"  ");
            }
            System.out.println();
            OrderItem orderItem = new OrderItem();
            Product product = new Product();
            BeanUtils.populate(orderItem,map);
            BeanUtils.populate(product,map);
            orderItem.setProduct(product);
        }
    }

}
