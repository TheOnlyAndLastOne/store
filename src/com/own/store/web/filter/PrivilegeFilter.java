package com.own.store.web.filter;

import com.own.store.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class PrivilegeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        User user = (User) request.getSession().getAttribute("loginUser");
        if(null!=user){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            request.setAttribute("msg","请登陆后再访问");
            request.getRequestDispatcher("/jsp/info.jsp").forward(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
