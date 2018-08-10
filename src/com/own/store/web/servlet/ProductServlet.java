package com.own.store.web.servlet;

import com.own.store.domain.PageModel;
import com.own.store.domain.Product;
import com.own.store.service.ProductService;
import com.own.store.service.serviceImpl.ProductServiceImpl;
import com.own.store.web.base.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/10 0010 10:56
 * @Description:
 */
public class ProductServlet extends BaseServlet {

    //商品详情
    public String findProductByPid(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pid = request.getParameter("pid");
        ProductService productService = new ProductServiceImpl();
        Product product = productService.findProductByPid(pid);
        request.setAttribute("product",product);
        return "/jsp/product_info.jsp";
    }

    //商品列表分页
    public String findProductsByCidWithPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String cid = request.getParameter("cid");
        String num = request.getParameter("num");
        ProductService productService = new ProductServiceImpl();
        //返回数据：1.当前页商品信息、2.分页信息、3.url
        PageModel pm = productService.findProductsByCidWithPage(cid,Integer.parseInt(num));
        request.setAttribute("page",pm);
        System.out.println("pagemodel~~~~~~~~~~~~~~~~~~~~~:"+pm);
        return "/jsp/product_list.jsp";
    }
}






















