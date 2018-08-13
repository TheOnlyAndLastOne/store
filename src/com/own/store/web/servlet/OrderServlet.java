package com.own.store.web.servlet;

import com.own.store.domain.*;
import com.own.store.service.OrderService;
import com.own.store.service.serviceImpl.OrderServiceImpl;
import com.own.store.utils.PaymentUtil;
import com.own.store.utils.UUIDUtils;
import com.own.store.web.base.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/13 0013 11:52
 * @Description:
 */
public class OrderServlet extends BaseServlet {

    //根据购物车生成订单
    public String saveOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //确认登陆状态
        User user = (User)request.getSession().getAttribute("loginUser");
        if(null == user){
            request.setAttribute("msg","请登录之后再下单");
            return "/jsp/info.jsp";
        }
        //购物车
        Cart cart = (Cart)request.getSession().getAttribute("cart");

        //创建订单
        Order order = new Order();
        order.setOid(UUIDUtils.getId());
        order.setOrdertime(new Date());
        order.setTotal(cart.getTotal());
        order.setState(1);
        order.setUser(user);

        for(CartItem item : cart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setItemid(UUIDUtils.getId());
            orderItem.setQuantity(item.getNum());
            orderItem.setTotal(item.getSubTotal());
            orderItem.setProduct(item.getProduct());
            orderItem.setOrder(order);
            order.getList().add(orderItem);
        }

        //保存订单
        OrderService orderService = new OrderServiceImpl();
        orderService.saveOrder(order);

        //清空购物车
        cart.clearCart();

        //订单放入request
        request.setAttribute("order",order);

        //转发order_info.jsp
        return "/jsp/order_info.jsp";
    }

    //我的订单
    public String findMyOrdersWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User)request.getSession().getAttribute("loginUser");
        int num = Integer.parseInt(request.getParameter("num"));
        OrderService orderService = new OrderServiceImpl();
        //select * from orders where uid = ?
        PageModel page = orderService.findMyOrdersWithPage(user,num);
        request.setAttribute("page",page);
        return "/jsp/order_list.jsp";
    }

    //付款/订单详情
    public String findOrderByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String oid = request.getParameter("oid");
        OrderService orderService = new OrderServiceImpl();
        Order order = orderService.findOrderByOid(oid);
        request.setAttribute("order",order);
        return "/jsp/order_info.jsp";
    }

    //付款/订单详情
    public String payOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String oid = request.getParameter("oid");
        String address = request.getParameter("address");
        String name = request.getParameter("name");
        String telephone = request.getParameter("telephone");
        String pd_FrpId = request.getParameter("pd_FrpId");
        //更新信息
        OrderService orderService = new OrderServiceImpl();
        Order order = orderService.findOrderByOid(oid);
        order.setAddress(address);
        order.setName(name);
        order.setTelephone(telephone);
        orderService.updateOrder(order);

        //支付
        // 把付款所需要的参数准备好:
        String p0_Cmd = "Buy";
        //商户编号
        String p1_MerId = "10001126856";
        //订单编号
        String p2_Order = oid;
        //金额
        String p3_Amt = "0.01";
        String p4_Cur = "CNY";
        String p5_Pid = "";
        String p6_Pcat = "";
        String p7_Pdesc = "";
        //接受响应参数的Servlet
        String p8_Url = "http://localhost:8080/OrderServlet?method=callBack";
        String p9_SAF = "";
        String pa_MP = "";
        String pr_NeedResponse = "1";
        //公司的秘钥
        String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";

        //调用易宝的加密算法,对所有数据进行加密,返回电子签名
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

        StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
        sb.append("p0_Cmd=").append(p0_Cmd).append("&");
        sb.append("p1_MerId=").append(p1_MerId).append("&");
        sb.append("p2_Order=").append(p2_Order).append("&");
        sb.append("p3_Amt=").append(p3_Amt).append("&");
        sb.append("p4_Cur=").append(p4_Cur).append("&");
        sb.append("p5_Pid=").append(p5_Pid).append("&");
        sb.append("p6_Pcat=").append(p6_Pcat).append("&");
        sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
        sb.append("p8_Url=").append(p8_Url).append("&");
        sb.append("p9_SAF=").append(p9_SAF).append("&");
        sb.append("pa_MP=").append(pa_MP).append("&");
        sb.append("pd_FrpId=").append(pd_FrpId).append("&");
        sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
        sb.append("hmac=").append(hmac);

        System.out.println(sb.toString());
        // 使用重定向：
        response.sendRedirect(sb.toString());

        //response.sendRedirect("https://www.yeepay.com/app-merchant-proxy/node?p0_cmd=Buy&p1_MerId=111111&k1=v1&k2=v2");
        return null;
    }
}
