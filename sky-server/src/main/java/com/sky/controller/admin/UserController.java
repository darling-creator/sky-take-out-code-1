package com.sky.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;

@RestController
@RequestMapping("/user/user")
public class UserController {
	@Autowired
    private UserService userService;
	@Autowired
    private JwtProperties jwtProperties;
	
    //小程序微信登录
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){

    	//调用service完成微信登录业务
        User user = userService.wxLogin(userLoginDTO);

        //存放userId
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId", user.getId());

        //提取jwt配置到局部变量
        String secretKey = jwtProperties.getUserSecretKey();
        long ttlMillis = jwtProperties.getUserTtl();

        //生成token
        String token = JwtUtil.createJWT(secretKey, ttlMillis, claims);

        //new + set 组装返回VO
        UserLoginVO vo = new UserLoginVO();
        vo.setId(user.getId());
        vo.setOpenid(user.getOpenid());
        vo.setToken(token);

        return Result.success(vo);
    }
}
