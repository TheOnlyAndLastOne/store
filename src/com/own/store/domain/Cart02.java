package com.own.store.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/10 0010 15:06
 * @Description:
 */
public class Cart02 {
    //个数不确定的购物项
    private double total;
    //总计、积分
    private List<CartItem> list = new ArrayList();
    //添加
    public void addCartItem(CartItem cartItem){
        //先判断之前是否买过该商品，没有买过直接加入，买过则在原数量上加本次数量
        boolean flag = false;
        CartItem old = null;
        for(CartItem cartItem2 : list){
            if(cartItem2.getProduct().getPid().equals(cartItem.getProduct().getPid())){
                flag = true;
                old = cartItem2;
            }
        }
        if(flag == false){
            list.add(cartItem);
        }else{
            old.setNum(old.getNum()+cartItem.getNum());
        }
    }

    //删除
    public void removeCartItem(String pid){
        for(CartItem cartItem : list){
            if(cartItem.getProduct().getPid().equals(pid)){

            }
        }
    }

    //清空
    public void clearCart(){
        list.clear();
    }
}
