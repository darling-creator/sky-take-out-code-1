package com.sky.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.sky.entity.Orders;

public interface OrdersService {
	//用户下单
	public void add(Orders orders);
	//修改订单	
	public void update(Orders orders);
	//根据订单id查询对应订单
	public Orders getById(Long id);
	//根据用户id查询该用户所有订单
	public List<Orders> getAllOrdersByUserId(Long userId);
	//根据订单号查询订单
	public Orders getOrdersByNumber(String number);
	//用户取消订单
	public void cancelOrder(Long orderId);
	//支付成功方法
	public void payOrder(String number);
	//统计传入时间段的营业额
	public BigDecimal getAmount(LocalDateTime beginTime,LocalDateTime endTime);
}
