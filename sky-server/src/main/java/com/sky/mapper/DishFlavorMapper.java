package com.sky.mapper;

import java.util.List;

import com.sky.entity.DishFlavor;

import io.lettuce.core.dynamic.annotation.Param;

public interface DishFlavorMapper {
    //根据菜品id删除口味
    public void deleteByDishId(Long dishId);
    //根据菜品id查询口味
    public List<DishFlavor> getByDishId(Long dishId);
    //批量插入口味
    public void insertBatch(@Param("flavors") List<DishFlavor> flavors);
}
