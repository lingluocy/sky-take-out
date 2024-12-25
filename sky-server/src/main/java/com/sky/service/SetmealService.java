package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

public interface SetmealService {
    void addSetmeal(SetmealDTO setmealDto);

    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    SetmealVO getById(Long id);

    void delete(Long[] ids);

    void startOrStop(Integer status, Long id);

    void update(SetmealDTO setmealDto);
}