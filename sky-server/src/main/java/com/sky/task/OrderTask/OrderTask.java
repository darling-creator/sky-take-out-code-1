package com.sky.task.OrderTask;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sky.entity.Orders;
import com.sky.mapper.OrdersMapper;

@Component
public class OrderTask {
	@Autowired
    private OrdersMapper ordersMapper;
	
	//查询的思路以及部分代码
		//处理支付超时订单规则：下单超过15分钟，状态设置为待付款(PENDING_PAYMENT=1)，自动取消，cron：每分钟执行一次
	//cron表达式格式：**`秒  分  时  日  月  周`**，一共 6 个位置，空格隔开
	//"0 * * * * ?"表示每分钟执行一次
	@Scheduled(cron = "0 * * * * ?")
	public void timeCheckOne() {
		//拿到15分钟之前的时间
        LocalDateTime fifteenAgo = LocalDateTime.now().minusMinutes(15);
        
        //查询状态待付款，并且下单时间在15分钟之前的订单
        List<Orders> list = ordersMapper.queryStatusAndTime(Orders.PENDING_PAYMENT, fifteenAgo);
        
        if( list != null && list.size() > 0 ) {
        	for( Orders order : list  ) {
        		order.setStatus(Orders.CANCELLED);
                order.setCancelReason("支付超时，系统自动取消");
                order.setCancelTime(LocalDateTime.now());
                ordersMapper.updateOrders(order);
        	}
        }
	}
	
	//下单是0 点之前，并且还停留在派送中，而且从下单算起已经超过 1 小时的订单，才改成已完成
	@Scheduled(cron = "* * 1 * * ?")
	public void timeCheckTwo() {
		//拿到60分钟之前的时间
        LocalDateTime oneHourAgo = LocalDateTime.now().minusMinutes(60);
        
        //查询那些显示还在派送中的订单
        List<Orders> list = ordersMapper.queryStatusAndTime(Orders.DELIVERY_IN_PROGRESS,oneHourAgo);
        
        if( list != null && list.size() > 0 ) {
        	for( Orders order : list ) {
        		order.setStatus(Orders.COMPLETED);
                ordersMapper.updateOrders(order);
        	}
        }
	}
}
