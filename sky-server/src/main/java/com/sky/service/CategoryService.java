package com.sky.service;

import java.util.List;

import com.sky.entity.Category;

public interface CategoryService {
	//新增分类
	public void insert(Category category);
	//修改分类
	public void update(Category category);
	//根据id删除
	public void deleteById(Long id);
	//更新状态
	public void updateStatus(Long id, Integer status);
	//根据类型查询分类列表
	public List<Category> listByType(Integer type);
}
