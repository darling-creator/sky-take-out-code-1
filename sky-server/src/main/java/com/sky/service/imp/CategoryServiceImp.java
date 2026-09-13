package com.sky.service.imp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.entity.Category;
import com.sky.mapper.CatagoryMapper;
import com.sky.service.CategoryService;

@Service
public class CategoryServiceImp implements CategoryService {
	@Autowired
	private CatagoryMapper categoryMapper;
	
	//新增分类
	@Override
	public void insert(Category category) {
		category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.insert(category);
	}
	
	//修改分类
	@Override
	public void update(Category category) {
		category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
	}
	
	//根据id删除
	@Override
	public void deleteById(Long id) {
		categoryMapper.deleteById(id);
	}
	
	//根据类型查询分类列表
	@Override
	public List<Category> listByType(Integer type) {
		return categoryMapper.listByType(type);
	}
	
	//更新状态
	@Override
	public void updateStatus(Long id, Integer status) {
		Category category = new Category();
        category.setId(id);
        category.setStatus(status);
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
	}
	
}
