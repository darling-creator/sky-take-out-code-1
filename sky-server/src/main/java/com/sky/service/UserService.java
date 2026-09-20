package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

public interface UserService {
	//微信小程序登录
	public User wxLogin(UserLoginDTO userLoginDTO);
}
