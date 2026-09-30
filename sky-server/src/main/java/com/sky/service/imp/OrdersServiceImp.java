package com.sky.service.imp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.context.BaseContext;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrdersMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.OrdersService;

@Service
public class OrdersServiceImp implements OrdersService {
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;
	
	//用户下单
	@Override
	public void add(Orders orders) {
		//获取当前登录用户id
        Long userId = BaseContext.getCurrentId();
        //查询的生成订单号的代码
        String orderNumber = UUID.randomUUID().toString().replace("-","");
        
        //设置订单基础状态
        orders.setUserId(userId);
        orders.setNumber(orderNumber);
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setPayStatus(Orders.UN_PAID);
        orders.setOrderTime(LocalDateTime.now());
        
        //插入主订单
        ordersMapper.insertOrders(orders);
        
        //拿到数据库生成的订单主键id
        Long orderId = orders.getId();
        
        //查询当前用户的全部购物车数据
        //在下单之后将购物车里面的数据全部转入订单详细中并清空购物车
        List<ShoppingCart> cartList = shoppingCartMapper.listByUserId(userId);
        List<OrderDetail> detailList = new ArrayList<>();
        for( ShoppingCart cart : cartList ) {
        	OrderDetail detail = new OrderDetail();
            detail.setOrderId(orderId);
            detail.setName(cart.getName());
            detail.setImage(cart.getImage());
            detail.setDishId(cart.getDishId());
            detail.setSetmealId(cart.getSetmealId());
            detail.setDishFlavor(cart.getDishFlavor());
            detail.setNumber(cart.getNumber());
            detail.setAmount(cart.getAmount());
            detailList.add(detail);
        }
        
        //批量插入订单明细
        orderDetailMapper.insertBatch(detailList);
        
        //下单完成，清空该用户购物车
        shoppingCartMapper.deleteByUserId(userId);
	}
	
	//修改订单	
	@Override
	public void update(Orders orders) {
		ordersMapper.updateOrders(orders);
	}
	
	//根据订单id查询对应订单
	@Override
	public Orders getById(Long id) {
		return ordersMapper.queryById(id);
	}
	
	//根据用户id查询该用户所有订单
	@Override
	public List<Orders> getAllOrdersByUserId(Long userId) {
		return ordersMapper.queryAllOrdersByUserId(userId);
	}
	
	//根据订单号查询订单
	@Override
	public Orders getOrdersByNumber(String number) {
		return ordersMapper.queryOrdersByNumber(number);
	}
	
	//用户取消订单
	@Override
	public void cancelOrder(Long orderId) {
		Orders orders = new Orders();
        orders.setId(orderId);
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelTime(LocalDateTime.now());
        orders.setCancelReason("用户手动取消");
        ordersMapper.updateOrders(orders);
	}
	
}
