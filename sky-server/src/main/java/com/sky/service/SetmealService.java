package com.sky.service;

import com.sky.entity.Setmeal;
import com.sky.result.PageResult;

public interface SetmealService {
	//分页查询
	public PageResult pageQuery(Integer pageNum, Integer pageSize, String name, Long categoryId, Integer status);

    //新增套餐
	public void saveWithDish(Setmeal setmeal);

    //查询套餐和关联菜品
	public Setmeal getByIdWithDish(Long id);

    //修改套餐以及套餐菜品
	public void updateWithDish(Setmeal setmeal);

    //删除套餐和关联菜品
	public void deleteById(Long id);

    //套餐启用停用
	public void updateStatus(Long id, Integer status);
}
