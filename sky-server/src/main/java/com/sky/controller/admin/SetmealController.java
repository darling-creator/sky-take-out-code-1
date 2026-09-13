package com.sky.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;

@RestController
@RequestMapping("/admin/setmeal")
public class SetmealController {
	@Autowired
    private SetmealService setmealService;
	
	@GetMapping("/page")
    public Result<PageResult> page(Integer pageNum,
                                   Integer pageSize,
                                   String name,
                                   Long categoryId,
                                   Integer status){
        PageResult pageResult = setmealService.pageQuery(pageNum,pageSize,name,categoryId,status);
        return Result.success(pageResult);
    }

    //新增套餐
    @SuppressWarnings("rawtypes")
	@PostMapping
    public Result save(@RequestBody Setmeal setmeal){
        setmealService.saveWithDish(setmeal);
        return Result.success();
    }

    //根据id查询套餐
    @GetMapping
    public Result<Setmeal> getById(Long id){
        Setmeal setmeal = setmealService.getByIdWithDish(id);
        return Result.success(setmeal);
    }

    //修改套餐
    @SuppressWarnings("rawtypes")
	@PutMapping
    public Result update(@RequestBody Setmeal setmeal){
        setmealService.updateWithDish(setmeal);
        return Result.success();
    }

    //删除套餐
    @SuppressWarnings("rawtypes")
	@DeleteMapping
    public Result delete(Long id){
        setmealService.deleteById(id);
        return Result.success();
    }

    //启用/停用套餐
    @SuppressWarnings("rawtypes")
	@PutMapping("/status")
    public Result updateStatus(Long id,Integer status){
        setmealService.updateStatus(id, status);
        return Result.success();
    }
}
