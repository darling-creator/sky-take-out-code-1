package com.sky.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;

@RestController
@RequestMapping("/admin/dish")
public class DishController {
	@Autowired
    private DishService dishService;
	
	//分页查询菜品
    @GetMapping("/page")
    public Result<PageResult> page(Integer pageNum,
                                   Integer pageSize,
                                   String name,
                                   Long categoryId,
                                   Integer status){
        PageResult pageResult = dishService.pageQuery(pageNum,pageSize,name,categoryId,status);
        return Result.success(pageResult);
    }

    //新增菜品
    @SuppressWarnings("rawtypes")
	@PostMapping
    public Result save(@RequestBody Dish dish){
        dishService.saveWithFlavor(dish);
        return Result.success();
    }

    //根据id查询菜品
    @GetMapping
    public Result<Dish> getById(Long id){
        Dish dish = dishService.getByIdWithFlavor(id);
        return Result.success(dish);
    }

    //修改菜品
    @SuppressWarnings("rawtypes")
	@PutMapping
    public Result update(@RequestBody Dish dish){
        dishService.updateWithFlavor(dish);
        return Result.success();
    }

    //删除菜品
    @SuppressWarnings("rawtypes")
	@DeleteMapping
    public Result delete(Long id){
        dishService.deleteById(id);
        return Result.success();
    }

    //菜品起售/停售
    @SuppressWarnings("rawtypes")
	@PutMapping("/status")
    public Result updateStatus(Long id,Integer status){
        dishService.updateStatus(id, status);
        return Result.success();
    }
}
