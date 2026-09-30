package com.sky.mapper;

import java.util.List;

import com.sky.entity.OrderDetail;

import io.lettuce.core.dynamic.annotation.Param;

public interface OrderDetailMapper {
	//批量插入订单明细
	public void insertBatch(@Param("list")List<OrderDetail>  orderDetailList);
	//根据订单id查询明细
	public List<OrderDetail> queryDetailById(Long orderId);
}
