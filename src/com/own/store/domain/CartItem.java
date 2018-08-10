package com.own.store.domain;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/10 0010 15:04
 * @Description:
 */
public class CartItem {
    private Product product;
    private int num;//商品数量
    private double subTotal;//小计

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    //小计经过计算可以获取到
    public double getSubTotal() {
        return product.getShop_price()*num;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
