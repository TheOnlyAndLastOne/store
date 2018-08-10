package com.own.store.web.servlet;

import com.own.store.domain.Category;
import com.own.store.service.CategoryService;
import com.own.store.service.serviceImpl.CategoryServiceImpl;
import com.own.store.utils.JedisUtils;
import com.own.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/10 0010 9:22
 * @Description:
 */
public class CategoryServlet extends BaseServlet {

    //获取全部分类
    public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Jedis jedis = JedisUtils.getJedis();
        String jsonStr = jedis.get("allcats");
        if(null == jsonStr || "".equals(jsonStr)){
            System.out.println("redis没有缓存");
            //获取分类
            //转为json数据
            CategoryService categoryService = new CategoryServiceImpl();
            List<Category> list = categoryService.getAllCats();
            jsonStr = JSONArray.fromObject(list).toString();
            jedis.set("allcats",jsonStr);
        }else{
            System.out.println("redis有缓存");
        }
        response.setContentType("application/json;charset=utf-8");
        //response.getWriter().print(jsonStr);
        response.getWriter().write(jsonStr);
        return null;
    }

}
