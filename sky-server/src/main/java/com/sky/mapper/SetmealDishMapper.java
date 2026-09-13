package com.sky.mapper;

import java.util.List;

import com.sky.entity.SetmealDish;

import io.lettuce.core.dynamic.annotation.Param;

public interface SetmealDishMapper {
	//批量新增套餐菜品
    public void insertBatch(@Param("list") List<SetmealDish> list);

    //根据套餐id删除所有关联菜品
    public void deleteBySetmealId(Long setmealId);

    //根据套餐id查询套餐菜品
    public List<SetmealDish> getBySetmealId(Long setmealId);
}
