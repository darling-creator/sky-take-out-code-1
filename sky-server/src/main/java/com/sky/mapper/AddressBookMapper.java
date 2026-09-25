package com.sky.mapper;

import java.util.List;

import com.sky.entity.AddressBook;

public interface AddressBookMapper {
	//新增收货地址
	public void insert(AddressBook addressBook);
	//修改收货地址
	public void update(AddressBook addressBook);
	//根据id删除地址
	public void delete(Long id);
	//根据用户id查询该用户下所有地址
	public List<AddressBook> queryAllAddressByUserId(Long userId);
	//查询用户的默认地址
	public AddressBook queryDefaultAddress(Long userId);
	//修改用户所有地址为非默认（再调用更新方法修改某条地址为默认）
	public void cancelDefaultAddress(Long userId);
}
