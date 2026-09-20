package com.sky.mapper;

import com.sky.entity.User;

public interface UserMapper {
	//根据openid查询用户
	public User getByOpenid(String openid);
	//新增用户
    public void insert(User user);
}
