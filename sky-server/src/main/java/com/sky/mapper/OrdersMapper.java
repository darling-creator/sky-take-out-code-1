package com.sky.mapper;

import java.util.List;

import com.sky.entity.Orders;

public interface OrdersMapper {
	//添加订单
	public void insertOrders(Orders orders);
	//修改订单	
	public void updateOrders(Orders orders);
	//根据订单id查询对应订单
	public Orders queryById(Long id);
	//根据用户id查询该用户所有订单
	public List<Orders> queryAllOrdersByUserId(Long userId);
	//根据订单号查询订单
	public Orders queryOrdersByNumber(String number);
}
