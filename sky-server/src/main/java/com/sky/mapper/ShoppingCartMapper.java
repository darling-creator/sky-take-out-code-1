package com.sky.mapper;

import java.util.List;

import com.sky.entity.ShoppingCart;

import io.lettuce.core.dynamic.annotation.Param;

public interface ShoppingCartMapper {
	//新增购物车记录
	public void insert(ShoppingCart shoppingCart);
	//修改购物车数量
	public void update(ShoppingCart shoppingCart);
	//删除单条购物车
	public void deleteById(Long id);
	//清空用户购物车
	public void deleteByUserId(Long userId);
	//查询该用户全部购物车
	public List<ShoppingCart> listByUserId(Long userId);
	//根据用户id、菜品id、套餐id、口味查询购物车记录
	public ShoppingCart getByIdAndFlavor(@Param("userId") Long userId,
            @Param("dishId") Long dishId,
            @Param("setmealId") Long setmealId,
            @Param("dishFlavor") String dishFlavor);
}
