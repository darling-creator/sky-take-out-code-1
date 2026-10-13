package com.sky.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sky.context.BaseContext;
import com.sky.entity.Orders;
import com.sky.result.Result;
import com.sky.service.OrdersService;
import com.sky.webSocket.WebSocketServer;

@RestController
@RequestMapping("/user/order")
public class OrdersController {
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private WebSocketServer webSocketServer;
	
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
	
	//用户成功下单
	@PutMapping("/paySuccess")
    public Result<String> paySuccess(@RequestParam String number){
        ordersService.payOrder(number);
        return Result.success("支付成功");
    }
	
	//用户催单
	@SuppressWarnings("rawtypes")
	@GetMapping("/reminder")
	public Result reminder(@RequestParam String orderNumber) {
		//组装消息发给商家
        String msg = "【催单提醒】订单号："+ orderNumber + "，用户正在催单，请尽快处理！";
        webSocketServer.sentToAllOnline(msg);
        return Result.success("催单成功");
	}
}
