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

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
	@Autowired
    private CategoryService categoryService;
	
	//新增分类
    @SuppressWarnings("rawtypes")
	@PostMapping
    public Result add(@RequestBody Category category){
        categoryService.insert(category);
        return Result.success();
    }

    //修改分类
    @SuppressWarnings("rawtypes")
	@PutMapping
    public Result update(@RequestBody Category category){
        categoryService.update(category);
        return Result.success();
    }

    //删除分类
    @SuppressWarnings("rawtypes")
	@DeleteMapping
    public Result delete(Long id){
        categoryService.deleteById(id);
        return Result.success();
    }

    //更新状态
    @SuppressWarnings("rawtypes")
	@PutMapping("/status")
    public Result updateStatus(Long id,Integer status){
        categoryService.updateStatus(id, status);
        return Result.success();
    }

    //根据类型查询分类列表
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.listByType(type);
        return Result.success(list);
    }
}
