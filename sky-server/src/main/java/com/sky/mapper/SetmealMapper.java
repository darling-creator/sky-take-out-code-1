package com.sky.mapper;

import java.util.List;

import com.sky.entity.Setmeal;

import io.lettuce.core.dynamic.annotation.Param;

public interface SetmealMapper {
	//套餐分页查询
    public List<Setmeal> page(@Param("name") String name,
                       @Param("categoryId") Long categoryId,
                       @Param("status") Integer status,
                       @Param("start") Integer start,
                       @Param("pageSize") Integer pageSize);

    public Long count(@Param("name") String name,
               @Param("categoryId") Long categoryId,
               @Param("status") Integer status);

    //新增套餐
    public void insert(Setmeal setmeal);

    //修改套餐
    public void update(Setmeal setmeal);

    //根据id查询套餐
    public Setmeal getById(Long id);

    //删除套餐
    public void deleteById(Long id);
}
