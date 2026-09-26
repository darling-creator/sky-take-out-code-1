package com.sky.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;

@RestController
@RequestMapping("/user/addressBook")
public class AddressBookController {
	@Autowired
    private AddressBookService addressBookService;
	
	//添加新的地址
	@SuppressWarnings("rawtypes")
	@PostMapping
    public Result save(@RequestBody AddressBook addressBook){
        addressBookService.save(addressBook);
        return Result.success();
    }
	
	//更新地址
    @SuppressWarnings("rawtypes")
	@PutMapping
    public Result update(@RequestBody AddressBook addressBook){
        addressBookService.update(addressBook);
        return Result.success();
    }
    
    //删除地址
    @SuppressWarnings({ "rawtypes" })
	@DeleteMapping
    public Result delete(Long id){
        addressBookService.delete(id);
        return Result.success();
    }
    
    //查询所有地址
    @GetMapping
    public Result<List<AddressBook>> list(){
        Long userId = BaseContext.getCurrentId();
        List<AddressBook> list = addressBookService.list(userId);
        return Result.success(list);
    }
    
    //查询默认地址
    @GetMapping
    public Result<AddressBook> getDefault(){
        Long userId = BaseContext.getCurrentId();
        AddressBook addressBook = addressBookService.getDefault(userId);
        return Result.success(addressBook);
    }
    
    //设置默认地址
    @SuppressWarnings("rawtypes")
	@PutMapping
    public Result setDefault(@RequestBody AddressBook addressBook){
        Long userId = BaseContext.getCurrentId();
        //先全部设置为非默认
        addressBookService.setDefault(userId);

        //再将选中地址设为默认
        addressBook.setIsDefault(1);
        addressBookService.update(addressBook);
        
        return Result.success();
    }
}
