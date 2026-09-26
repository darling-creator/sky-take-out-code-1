package com.sky.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;

@Service
public class AddressBookServiceImp implements AddressBookService {
	@Autowired
	private AddressBookMapper addressBookMapper;
	
	//新增收货地址
	@Override
	public void save(AddressBook addressBook) {
		//获取当前登录用户id，存入地址
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        //默认新增地址不是默认地址
        addressBook.setIsDefault(0);
        addressBookMapper.insert(addressBook);
	}
	
	//更新收货地址
	@Override
	public void update(AddressBook addressBook) {
		addressBookMapper.update(addressBook);
	}
	
	//根据id删除地址
	@Override
	public void delete(Long id) {
		addressBookMapper.delete(id);
	}
	
	//根据用户id查询该用户下所有地址
	@Override
	public List<AddressBook> list(Long userId) {
		return addressBookMapper.queryAllAddressByUserId(userId);
	}
	
	//查询用户的默认地址
	@Override
	public AddressBook getDefault(Long userId) {
		return addressBookMapper.queryDefaultAddress(userId);
	}
	
	//修改用户所有地址为非默认
	@Override
	public void setDefault(Long userId) {
		addressBookMapper.cancelDefaultAddress(userId);
	}
}
