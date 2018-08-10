package com.own.store.dao.daoImpl;

import com.own.store.dao.ProductDao;
import com.own.store.domain.Product;
import com.own.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/10 0010 10:58
 * @Description:
 */
public class ProductDaoImpl implements ProductDao {
    //最热商品
    @Override
    public List<Product> findHots() throws SQLException {
        String sql = "select * from product where pflag = 0 order by pdate desc limit 0,9 ";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        return qr.query(sql,new BeanListHandler<>(Product.class));
    }

    //最新商品
    @Override
    public List<Product> findNews() throws SQLException {
        String sql = "select * from product where pflag = 0 and is_hot = 1 order by pdate desc limit 0,9 ";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        return qr.query(sql,new BeanListHandler<>(Product.class));
    }

    //商品详情
    @Override
    public Product findProductByPid(String pid) throws SQLException {
        String sql = "select * from product where pid = ?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        return qr.query(sql,new BeanHandler<>(Product.class),pid);
    }

    @Override
    public int findProductTotal(String cid) throws SQLException {
        String sql = "select count(*) from product where cid = ?";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        Long num =  (Long)qr.query(sql,new ScalarHandler(),cid);
        return num.intValue();
    }

    @Override
    public List findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws SQLException {
        String sql = "select * from product where cid = ? limit ?,? ";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        return qr.query(sql,new BeanListHandler<>(Product.class),cid,startIndex,pageSize);
    }
}

























