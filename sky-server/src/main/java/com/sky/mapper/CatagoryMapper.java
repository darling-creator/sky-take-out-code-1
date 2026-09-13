package com.sky.mapper;

import java.util.List;

import com.sky.entity.Category;

public interface CatagoryMapper {
	//新增分类
	public void insert(Category category);
	//修改分类
	public void update(Category category);
	//根据id删除
	public void deleteById(Long id);
	//根据id查询
	public Category getById(Long id);
	//根据类型查询分类列表
    public List<Category> listByType(Integer type);
}
