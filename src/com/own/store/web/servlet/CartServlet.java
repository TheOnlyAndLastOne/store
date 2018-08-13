package com.own.store.web.servlet;

import com.own.store.domain.Cart;
import com.own.store.domain.CartItem;
import com.own.store.domain.Product;
import com.own.store.service.ProductService;
import com.own.store.service.serviceImpl.ProductServiceImpl;
import com.own.store.web.base.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/13 0013 10:11
 * @Description:
 */
public class CartServlet extends BaseServlet {

    //添加购物项到购物车
    public String addCartItemToCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if(null==cart){
            cart = new Cart();
            request.getSession().setAttribute("cart",cart);
        }
        String pid = request.getParameter("pid");
        int num = Integer.parseInt(request.getParameter("quantity"));
        ProductService productService = new ProductServiceImpl();
        Product product= productService.findProductByPid(pid);
        CartItem cartItem = new CartItem();
        cartItem.setNum(num);
        cartItem.setProduct(product);
        cart.addCartItem(cartItem);

        response.sendRedirect("/jsp/cart.jsp");
        return null;
    }

    //删除购物车购物项
    public String removeCartItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pid = request.getParameter("id");
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        cart.removeCartItem(pid);
        response.sendRedirect("/jsp/cart.jsp");
        return null;
    }

    //清除购物车
    public String clearCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        cart.clearCart();
        response.sendRedirect("/jsp/cart.jsp");
        return null;
    }

}
