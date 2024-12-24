package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.DishSetmealMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealMapper setmealMapper;
    @Autowired
    DishSetmealMapper dishSetmealMapper;

    @Override
    @Transactional
    public void addSetmeal(SetmealDTO setmealDto) {
	Setmeal setmeal = new Setmeal();
	BeanUtils.copyProperties(setmealDto, setmeal);
	log.info("开始修改setmeal");
	setmealMapper.addSetmeal(setmeal);
	
	Long id = setmeal.getId();
	log.info("id:{}", id);
	if (id == null || id <= 0) {
	    throw new RuntimeException("套餐ID无效");
	}
	
	log.info("开始修改setmeal_dish");
	List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
	for (SetmealDish setmealDish : setmealDishes) {
	    setmealDish.setSetmealId(id);
	}

 	dishSetmealMapper.addDishSetmeal(setmealDishes);
    }

    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
	PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

	Page<Setmeal> page = setmealMapper.page(setmealPageQueryDTO);

	return new PageResult(page.getTotal(), page.getResult());
    }
}