package com.sky.service;

import com.sky.entity.Dish;
import com.sky.result.PageResult;

public interface DishService {
	//分页查询菜品
    public PageResult pageQuery(Integer pageNum, Integer pageSize, String name, Long categoryId, Integer status);

    //新增菜品，附带口味
    public void saveWithFlavor(Dish dish);

    //根据id查询菜品和口味（编辑回显）
    public Dish getByIdWithFlavor(Long id);

    //修改菜品和口味
    public void updateWithFlavor(Dish dish);

    //删除菜品+口味
    public void deleteById(Long id);

    //菜品起售停售
    public void updateStatus(Long id, Integer status);
}
