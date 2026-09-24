package com.sky.service.imp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.context.BaseContext;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImp implements ShoppingCartService {
	@Autowired
    private ShoppingCartMapper shoppingCartMapper;
	@Autowired
    private DishMapper dishMapper;
	@Autowired
    private SetmealMapper setmealMapper;
    
	//添加购物车
	@Override
	public void addShoppingCart(ShoppingCart shoppingCart) {
		//拿到当前登录用户id
        Long userId = BaseContext.getCurrentId();
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();
        String dishFlavor = shoppingCart.getDishFlavor();
        
        ShoppingCart cart = shoppingCartMapper.getByIdAndFlavor(userId,dishId,setmealId,dishFlavor);
        
        //如果有记录则数量加1
        if( cart != null ) {
        	cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.update(cart);
        } else {
        	//没有则新增购物车记录
        	ShoppingCart newCart = new ShoppingCart();
            newCart.setUserId(userId);
            newCart.setDishId(dishId);
            newCart.setSetmealId(setmealId);
            newCart.setDishFlavor(dishFlavor);
            newCart.setNumber(1);
            newCart.setCreateTime(LocalDateTime.now());
            
            //看添加的是菜品还是套餐
            if( dishId != null ){
                Dish dish = dishMapper.getById(dishId);
                newCart.setName(dish.getName());
                newCart.setImage(dish.getImage());
                newCart.setAmount(dish.getPrice());
            }else{
                Setmeal setmeal = setmealMapper.getById(setmealId);
                newCart.setName(setmeal.getName());
                newCart.setImage(setmeal.getImage());
                newCart.setAmount(setmeal.getPrice());
            }
            shoppingCartMapper.insert(newCart);
		}
	}
	
	//查询购物车列表
	@Override
	public List<ShoppingCart> list() {
		Long userId = BaseContext.getCurrentId();
        return shoppingCartMapper.listByUserId(userId);
	}
	
	//减少购物车商品数量
	@Override
	public void subShoppingCart(ShoppingCart shoppingCart) {
		//拿到当前登录用户id
        Long userId = BaseContext.getCurrentId();
        ShoppingCart cart = shoppingCartMapper.getByIdAndFlavor(
        		userId,
        		shoppingCart.getDishId(),
                shoppingCart.getSetmealId(),
                shoppingCart.getDishFlavor());
        
        if( cart == null ){
            return;
        }
        
        int num = cart.getNumber() - 1;
        
        //数量如果等于0，则删除该购物车
        if( num <=0 ){
            shoppingCartMapper.deleteById(cart.getId());
        }else{
            cart.setNumber(num);
            shoppingCartMapper.update(cart);
        }
	}
	
	//清空购物车
	@Override
	public void cleanShoppingCart() {
		Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(userId);
	}
	
}
