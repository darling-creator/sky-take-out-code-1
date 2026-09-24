package com.sky.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;

@RestController
@RequestMapping("/user/shoppingCart")
public class ShoppingCartController {
	@Autowired
    private ShoppingCartService shoppingCartService;
	
	//添加购物车
    @SuppressWarnings("rawtypes")
	@PostMapping("/add")
    public Result add(@RequestBody ShoppingCart shoppingCart){
        shoppingCartService.addShoppingCart(shoppingCart);
        return Result.success();
    }

    //查询购物车
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list(){
        List<ShoppingCart> list = shoppingCartService.list();
        return Result.success(list);
    }

    //减少购物车
    @SuppressWarnings("rawtypes")
	@PostMapping("/sub")
    public Result sub(@RequestBody ShoppingCart shoppingCart){
        shoppingCartService.subShoppingCart(shoppingCart);
        return Result.success();
    }

    //清空购物车
    @SuppressWarnings("rawtypes")
	@DeleteMapping("/clean")
    public Result clean(){
        shoppingCartService.cleanShoppingCart();
        return Result.success();
    }
}
