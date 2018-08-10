package com.own.store.web.servlet;

import com.own.store.domain.Product;
import com.own.store.service.ProductService;
import com.own.store.service.serviceImpl.ProductServiceImpl;
import com.own.store.web.base.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/10 0010 9:17
 * @Description:
 */
public class IndexServlet extends BaseServlet {

    //获取分类信息
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        //分类不在此处理，利用ajax异步请求，放入redis中
        /*//获取分类信息
        CategoryService categoryService = new CategoryServiceImpl();
        List<Category> list = categoryService.getAllCats();
        //集合放入request
        request.setAttribute("allcats",list);*/

        //最新最新、最热商品
        ProductService productService = new ProductServiceImpl();
        List<Product> hotList = productService.findHots();
        List<Product> newList = productService.findNews();
        request.setAttribute("hotList",hotList);
        request.setAttribute("newList",newList);

        //转发真实首页
        return "/jsp/index.jsp";
    }
}
