package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishSetmealMapper {

    
    @Select("select count(*) from setmeal_dish where dish_id = #{dishId};")
    public Long select(Long dishId);

    void addDishSetmeal(List<SetmealDish> setmealDishes);

    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getById(Long id);

    void delete(Long[] ids);
}