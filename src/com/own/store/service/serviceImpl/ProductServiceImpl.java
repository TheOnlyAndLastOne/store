package com.own.store.service.serviceImpl;

import com.own.store.dao.ProductDao;
import com.own.store.dao.daoImpl.ProductDaoImpl;
import com.own.store.domain.PageModel;
import com.own.store.domain.Product;
import com.own.store.service.ProductService;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/10 0010 10:56
 * @Description:
 */
public class ProductServiceImpl implements ProductService {

    ProductDao productDao = new ProductDaoImpl();

    //最热商品
    @Override
    public List<Product> findHots() throws SQLException {
        return productDao.findHots();
    }

    //最新商品
    @Override
    public List<Product> findNews() throws SQLException {
        return productDao.findNews();
    }

    //商品详情
    @Override
    public Product findProductByPid(String pid) throws SQLException {
        return productDao.findProductByPid(pid);
    }

    //分类商品列表
    @Override
    public PageModel findProductsByCidWithPage(String cid, int num) throws SQLException {
        //当前分类商品总数
        int total = productDao.findProductTotal(cid);
        PageModel pm = new PageModel(num,total,12);
        List list = productDao.findProductsByCidWithPage(cid,pm.getStartIndex(),pm.getPageSize());
        pm.setList(list);
        pm.setUrl("ProductServlet?method=findProductsByCidWithPage&cid="+cid);
        return pm;
    }
}





















