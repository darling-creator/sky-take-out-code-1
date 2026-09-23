package com.sky.service;

import java.util.List;

import com.sky.entity.ShoppingCart;

public interface ShoppingCartService {
	//添加购物车
    public void addShoppingCart(ShoppingCart shoppingCart);
    //查询购物车列表
    public List<ShoppingCart> list();
    //减少购物车商品数量
    public void subShoppingCart(ShoppingCart shoppingCart);
    //清空购物车
    public void cleanShoppingCart();
}
