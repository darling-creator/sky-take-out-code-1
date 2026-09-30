package com.sky.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.context.BaseContext;
import com.sky.entity.Orders;
import com.sky.result.Result;
import com.sky.service.OrdersService;

@RestController
@RequestMapping("/user/order")
public class OrdersController {
	@Autowired
	private OrdersService ordersService;
	
	//用户提交订单下单
	@PostMapping("/submit")
    public Result<String> submit(@RequestBody Orders orders){
        ordersService.add(orders);
        return Result.success("下单成功");
    }
	
	//根据id查询订单
	@GetMapping("/{id}")
    public Result<Orders> getOrderById(@PathVariable Long id){
        Orders orders = ordersService.getById(id);
        return Result.success(orders);
    }
	
	//查询全部订单
	@GetMapping("/list")
    public Result<List<Orders>> myOrderList(){
		Long userId = BaseContext.getCurrentId();
        List<Orders> list = ordersService.getAllOrdersByUserId(userId);
        return Result.success(list);
    }
	
	//用户取消订单
	@PutMapping("/cancel/{id}")
    public Result<String> cancel(@PathVariable Long id){
        ordersService.cancelOrder(id);
        return Result.success("订单取消成功");
    }
}
