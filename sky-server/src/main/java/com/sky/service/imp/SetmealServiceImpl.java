package com.sky.service.imp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;

@Service
public class SetmealServiceImpl implements SetmealService {
	@Autowired
    private SetmealMapper setmealMapper;
	@Autowired
    private SetmealDishMapper setmealDishMapper;
    
    
	@Override
	public PageResult pageQuery(Integer pageNum, Integer pageSize, String name, Long categoryId, Integer status) {
		int start = (pageNum - 1)*pageSize;
        List<Setmeal> list = setmealMapper.page(name,categoryId,status,start,pageSize);
        Long total = setmealMapper.count(name,categoryId,status);
        return new PageResult(total,list);
	}
	
	//新增套餐
	@Override
	public void saveWithDish(Setmeal setmeal) {
		setmeal.setCreateTime(LocalDateTime.now());
        setmeal.setUpdateTime(LocalDateTime.now());
        setmealMapper.insert(setmeal);

        List<SetmealDish> dishList = setmeal.getSetmealDishes();
        if( dishList != null && dishList.size()>0 ){
            Long setmealId = setmeal.getId();
            for( SetmealDish sd : dishList ){
                sd.setSetmealId(setmealId);
            }
            setmealDishMapper.insertBatch(dishList);
        }
	}
	
	//查询套餐和关联菜品
	@Override
	public Setmeal getByIdWithDish(Long id) {
		Setmeal setmeal = setmealMapper.getById(id);
        List<SetmealDish> dishList = setmealDishMapper.getBySetmealId(id);
        setmeal.setSetmealDishes(dishList);
        return setmeal;
	}
	
	//修改套餐以及套餐菜品
	@Override
	public void updateWithDish(Setmeal setmeal) {
		setmeal.setUpdateTime(LocalDateTime.now());
        setmealMapper.update(setmeal);
        //删除旧的套餐菜品
        setmealDishMapper.deleteBySetmealId(setmeal.getId());

        List<SetmealDish> dishList = setmeal.getSetmealDishes();
        if( dishList != null && dishList.size()>0 ){
            for( SetmealDish sd : dishList ){
                sd.setSetmealId(setmeal.getId());
            }
            setmealDishMapper.insertBatch(dishList);
        }
	}
	
	//删除套餐和关联菜品
	@Override
	public void deleteById(Long id) {
		setmealMapper.deleteById(id);
        setmealDishMapper.deleteBySetmealId(id);
	}
	
	//套餐启用停用
	@Override
	public void updateStatus(Long id, Integer status) {
		Setmeal setmeal = new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);
        setmeal.setUpdateTime(LocalDateTime.now());
        setmealMapper.update(setmeal);
	}
	
}
