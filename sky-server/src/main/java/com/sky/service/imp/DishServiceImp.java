package com.sky.service.imp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;

@Service
public class DishServiceImp implements DishService {
	@Autowired
    private DishMapper dishMapper;
	@Autowired
    private DishFlavorMapper dishFlavorMapper;
	
	//查询的分页代码
	@Override
	public PageResult pageQuery(Integer pageNum, Integer pageSize, String name, Long categoryId, Integer status) {
		int start = (pageNum -1)*pageSize;
        List<Dish> list = dishMapper.page(name,categoryId,status,start,pageSize);
        Long total = dishMapper.count(name,categoryId,status);
        return new PageResult(total,list);
	}
	
	//新增菜品+口味
	@Override
	public void saveWithFlavor(Dish dish) {
        dish.setCreateTime(LocalDateTime.now());
        dish.setUpdateTime(LocalDateTime.now());
        //插入菜品主表
        dishMapper.insert(dish);

        //拿到前端传过来的口味集合
        List<DishFlavor> flavors = dish.getFlavors();
        if( flavors != null && flavors.size()>0 ){
            //拿到刚插入菜品的主键id
            Long dishId = dish.getId();
            for( DishFlavor flavor : flavors ){
                flavor.setDishId(dishId);
            }
            //批量插入口味
            dishFlavorMapper.insertBatch(flavors);
        }
	}
	
	//根据id查询菜品+口味
	@Override
	public Dish getByIdWithFlavor(Long id) {
		//查询菜品基础信息
        Dish dish = dishMapper.getById(id);
        //根据菜品id查询所有口味
        List<DishFlavor> flavors = dishFlavorMapper.getByDishId(id);
        dish.setFlavors(flavors);
        return dish;
	}
	
	//修改菜品和口味
	@Override
	public void updateWithFlavor(Dish dish) {
		dish.setUpdateTime(LocalDateTime.now());
        //更新菜品基础信息
        dishMapper.update(dish);

        //先删除菜品旧的所有口味
        dishFlavorMapper.deleteByDishId(dish.getId());

        //新增新的口味
        List<DishFlavor> flavors = dish.getFlavors();
        if( flavors != null && flavors.size()>0 ){
            for( DishFlavor flavor : flavors ){
                flavor.setDishId(dish.getId());
            }
            dishFlavorMapper.insertBatch(flavors);
        }
	}
	
	//删除菜品，同时删除关联口味
	@Override
	public void deleteById(Long id) {
		dishMapper.deleteById(id);
        dishFlavorMapper.deleteByDishId(id);
	}
	
	//菜品起售停售
	@Override
	public void updateStatus(Long id, Integer status) {
		Dish dish = new Dish();
        dish.setId(id);
        dish.setStatus(status);
        dish.setUpdateTime(LocalDateTime.now());
        dishMapper.update(dish);
	}
	
	//根据分类id查询菜品，携带口味
	@Override
	public List<Dish> listWithFlavor(Long categoryId) {
		//只查询启用 status=1
        List<Dish> dishList = dishMapper.listByCategoryIdAndStatus(categoryId,1);
        List<Dish> voList = new ArrayList<>();

        for(Dish dish : dishList){
            Dish dish1 = new Dish();
            dish1.setId(dish.getId());
            dish1.setName(dish.getName());
            dish1.setCategoryId(dish.getCategoryId());
            dish1.setPrice(dish.getPrice());
            dish1.setImage(dish.getImage());
            dish1.setDescription(dish.getDescription());
            dish1.setStatus(dish.getStatus());

            //查询该菜品对应的口味
            List<DishFlavor> flavorList = dishFlavorMapper.getByDishId(dish.getId());
            dish1.setFlavors(flavorList);

            voList.add(dish1);
        }
        return voList;
	}
	
	
}
