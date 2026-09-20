package com.sky.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.result.Result;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/shop")
@Slf4j
public class AdminShopController {
	//redis固定key
    public static final String SHOP_STATUS_KEY = "SHOP_STATUS";

    @SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
    
    
    //设置店铺营业状态status 1营业 0打烊
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/{status}")
    public Result setShopStatus(@PathVariable Integer status){
        //写入redis string
        redisTemplate.opsForValue().set(SHOP_STATUS_KEY,status);
        return Result.success();
    }
}
