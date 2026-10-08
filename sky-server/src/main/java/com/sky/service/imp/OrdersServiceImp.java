package com.sky.service.imp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.AddressBookMapper;
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
	@Autowired
	private AddressBookMapper addressBookMapper;
	
	//用户下单
	@Override
	@Transactional
	public void add(Orders orders) {
		//获取当前登录用户id
        Long userId = BaseContext.getCurrentId();
        //查询的生成订单号的代码
        String orderNumber = UUID.randomUUID().toString().replace("-","");
        //根据地址用户id，查询收货地址
        AddressBook addressBook = addressBookMapper.queryDefaultAddress(orders.getUserId());
        
        //设置订单基础状态
        orders.setUserId(userId);
        orders.setNumber(orderNumber);
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setPayStatus(Orders.UN_PAID);
        orders.setOrderTime(LocalDateTime.now());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(addressBook.getProvinceName() + addressBook.getCityName()
        		+ addressBook.getDistrictName() + addressBook.getDetail());
        
        //查询当前用户的全部购物车数据
        List<ShoppingCart> cartList = shoppingCartMapper.listByUserId(userId);
        
        //计算总的金额
        //BigDecimal是 Java专门用来精确计算小数（钱、金额）的类型。
        //定义总金额，初始是0
        BigDecimal totalAmount = new BigDecimal("0");
        for( ShoppingCart cart : cartList ) {
        	//multiply()乘法
        	//add()加法
        	//subtract()减法
        	//divide()除法
        	//要求传入的参数必须是 BigDecimal类型
        	BigDecimal itemTotal = cart.getAmount().multiply(new BigDecimal(cart.getNumber()));
        	totalAmount = totalAmount.add(itemTotal);
        }
        orders.setAmount(totalAmount);
        
        //插入主订单
        ordersMapper.insertOrders(orders);
        
        //拿到数据库生成的订单主键id
        Long orderId = orders.getId();
        
        
        //在下单之后将购物车里面的数据全部转入订单详细中并清空购物车
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
	
	//支付成功方法
	@Override
	public void payOrder(String number) {
		//根据订单号查询订单
		Orders orders = ordersMapper.queryOrdersByNumber(number);
		
		//检查订单的状态，只有待付款才能支付
		if( !orders.getStatus().equals(Orders.PENDING_PAYMENT) ) {
			throw new RuntimeException("订单状态异常，无法支付");
		}
		
		//修改订单状态
		orders.setStatus(Orders.TO_BE_CONFIRMED);
		orders.setPayStatus(Orders.PAID);
		orders.setCheckoutTime(LocalDateTime.now());
		ordersMapper.updateOrders(orders);
	}
	
}
