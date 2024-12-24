package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

public interface SetmealService {
    void addSetmeal(SetmealDTO setmealDto);

    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);
}