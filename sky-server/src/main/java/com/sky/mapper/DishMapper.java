package com.sky.mapper;

import java.util.List;

import com.sky.entity.Dish;

import io.lettuce.core.dynamic.annotation.Param;

public interface DishMapper {
	//分页查询菜品
    public List<Dish> page(@Param("name") String name,
                    @Param("categoryId") Long categoryId,
                    @Param("status") Integer status,
                    @Param("start") Integer start,
                    @Param("pageSize") Integer pageSize);

    //统计总数
    public Long count(@Param("name") String name,
               @Param("categoryId") Long categoryId,
               @Param("status") Integer status);

    //新增菜品
    public void insert(Dish dish);

    //修改菜品
    public void update(Dish dish);

    //根据id查询菜品
    public Dish getById(Long id);

    //根据id删除菜品
    public void deleteById(Long id);
}
