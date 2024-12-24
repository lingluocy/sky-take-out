package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {


    void addBatch(List<DishFlavor> flavors,Long id);
    
    void update(DishFlavor flavor);

    @Select("select * from dish_flavor")
    List<DishFlavor> getById(Long id);

    void deleteBatch(Long[] ids);
}