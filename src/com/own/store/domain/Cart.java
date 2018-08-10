package com.own.store.domain;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/10 0010 15:22
 * @Description:
 */
public class Cart {
    //个数不确定的购物项
    private double total = 0;
    //总计、积分
    private Map<String,CartItem> map = new HashMap<>();
    //添加
    public void addCartItem(CartItem cartItem){
        //先判断之前是否买过该商品，没有买过直接加入，买过则在原数量上加本次数量
        String pid = cartItem.getProduct().getPid();
        if(map.containsKey(pid)){
            CartItem oldItem = map.get(pid);
            oldItem.setNum(oldItem.getNum()+cartItem.getNum());
        }else{
            map.put(pid,cartItem);
        }
    }

    //删除
    public void removeCartItem(String pid){
        map.remove(pid);
    }

    //清空
    public void clearCart(){
        map.clear();
    }



    public double getTotal() {
        total = 0;
        Collection<CartItem> collection = map.values();
        //将购物车上小计相加
        for(CartItem cartItem : collection){
            total += cartItem.getSubTotal();
        }
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Map<String, CartItem> getMap() {
        return map;
    }

    public void setMap(Map<String, CartItem> map) {
        this.map = map;
    }
}
