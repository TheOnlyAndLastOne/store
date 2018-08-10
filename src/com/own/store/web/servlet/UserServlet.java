package com.own.store.web.servlet;

import com.own.store.domain.User;
import com.own.store.service.UserService;
import com.own.store.service.serviceImpl.UserServiceImpl;
import com.own.store.utils.MailUtils;
import com.own.store.utils.MyBeanUtils;
import com.own.store.utils.UUIDUtils;
import com.own.store.web.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/9 0009 9:35
 * @Description:
 */
public class UserServlet extends BaseServlet {
    //跳转注册页面
    public String registerUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/jsp/register.jsp";
    }

    //跳转登陆页面
    public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/jsp/login.jsp";
    }

    //注册
    public String register(HttpServletRequest request, HttpServletResponse response){
        //接参
        Map<String, String[]> parameterMap = request.getParameterMap();

        User user = new User();

        MyBeanUtils.populate(user,parameterMap);

        //为其他信息赋值
        user.setUid(UUIDUtils.getId());
        user.setState(0);
        user.setCode(UUIDUtils.getCode());

        System.out.println(user );

        //遍历
        /*for(Map.Entry<String,String[]> entry : parameterMap.entrySet()){

        }*/
        /*Set<String> keySet = parameterMap.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()){
            String str = iterator.next();
            System.out.println(str);
            String[] strings = parameterMap.get(str);
            for(String string : strings){
                System.out.println(string);
            }
            System.out.println();
        }*/

        //注册
        UserService userService = new UserServiceImpl();

        try {
            userService.userRegister(user);
            MailUtils.sendMail(user.getEmail(),user.getCode());
            //成功，发送邮件，跳转提示页面
            request.setAttribute("msg","用户注册成功，请激活");
        } catch (Exception e) {
            request.setAttribute("msg","用户注册失败，请重新注册");
        }
        return "/jsp/info.jsp";
    }

    //激活
    public String active(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String code = request.getParameter("code");
        UserService userService = new UserServiceImpl();
        boolean flag = userService.userActive(code);
        if(flag){
            request.setAttribute("msg","激活成功，请登陆");
            return "/jsp/login.jsp";
        }else{
            request.setAttribute("msg","激活失败,请重新激活");
            return "/jsp/info.jsp";
        }
    }

    //登陆
    public String userLogin(HttpServletRequest request, HttpServletResponse response) throws Exception{
        User user = new User();
        BeanUtils.populate(user,request.getParameterMap());
        UserService userService = new UserServiceImpl();
        User user02 = null;
        try {
            user02 = userService.userLogin(user);
            //登陆成功,用户信息放入session
            request.getSession().setAttribute("loginUser",user02);
            response.sendRedirect("/index.jsp");
            return null;
        } catch (Exception e) {
            //登陆失败
            String msg = e.getMessage();
            System.out.println(msg);
            request.setAttribute("msg",msg);
            return "/jsp/login.jsp";
        }
    }

    //退出
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().invalidate();
        response.sendRedirect("/index.jsp");
        return null;
    }

    //待完成功能：自动登陆、记住用户名、异步校验用户是否存在
}
