package com.sky.service;

import java.util.List;

import com.sky.entity.AddressBook;

public interface AddressBookService {
	//新增收货地址
	public void save(AddressBook addressBook);
	//更新收货地址
	public void update(AddressBook addressBook);
	//根据id删除地址
	public void delete(Long id);
	//根据用户id查询该用户下所有地址
	public List<AddressBook> list(Long userId);
	//查询用户的默认地址
	public AddressBook getDefault(Long userId);
	//修改用户所有地址为非默认
	public void setDefault(Long userId);
}
