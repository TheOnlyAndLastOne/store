package com.own.store.test;

import com.own.store.domain.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/9 0009 10:16
 * @Description:
 */
public class TestBeanUtils {

    @Test
    public void test01(){
        Map<String,String[]> map = new HashMap<>();
        map.put("username",new String[]{"tom"});
        map.put("password",new String[]{"123"});

        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test02() throws InvocationTargetException, IllegalAccessException {
        Map<String,String[]> map = new HashMap<>();
        map.put("username",new String[]{"tom"});
        map.put("password",new String[]{"123"});
        map.put("birthday",new String[]{"2018-08-08"});

        User user = new User();
        DateConverter dt = new DateConverter();
        dt.setPattern("yyyy-MM-dd");
        ConvertUtils.register(dt,java.util.Date.class);
        BeanUtils.populate(user,map);
        System.out.println(user );
    }
}
