package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

public interface DishService {
    void addWithFlavor(DishDTO dishDTO);

    PageResult page(DishPageQueryDTO queryDTO);
    
    void updateStatus(Integer status, Long id);

    void update(DishDTO dishDTO);

    DishVO getById(Long id);
}