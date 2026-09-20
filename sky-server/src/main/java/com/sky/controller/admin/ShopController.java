package com.sky.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.result.Result;

@RestController
@RequestMapping("/user/shop")
public class ShopController {
	//redis固定key
	public static final String SHOP_STATUS_KEY = "SHOP_STATUS";
	
	@SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
	
	//用户端小程序查询店铺营业状态
	@GetMapping("/status")
    public Result<Integer> getShopStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(SHOP_STATUS_KEY);
        return Result.success(status);
    }
}
