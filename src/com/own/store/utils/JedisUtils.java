package com.own.store.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/10 0010 10:37
 * @Description:
 */
public class JedisUtils {
    //创建连接池
    private static JedisPoolConfig config;
    private static JedisPool pool;

    static{
        config=new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(2);

        /*pool=new JedisPool(config, "192.168.146.135", 6379);*/
        pool=new JedisPool(config, "127.0.0.1", 6379);
    }


    //获取连接的方法
    public static Jedis getJedis(){
        Jedis jedis = pool.getResource();
        jedis.auth("adminn");
        return jedis;
    }


    //释放连接
    public static void closeJedis(Jedis j){
        j.close();
    }
}
