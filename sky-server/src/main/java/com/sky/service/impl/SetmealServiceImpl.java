package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.DishSetmealMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
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
    @Autowired
    DishMapper dishMapper;

    @Transactional
    public void addSetmeal(SetmealDTO setmealDto) {
	Setmeal setmeal = new Setmeal();
	BeanUtils.copyProperties(setmealDto, setmeal);
	setmealMapper.addSetmeal(setmeal);

	Long id = setmeal.getId();

	List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
	for (SetmealDish setmealDish : setmealDishes) {
	    setmealDish.setSetmealId(id);
	}

	dishSetmealMapper.addDishSetmeal(setmealDishes);
    }

    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
	PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

	Page<SetmealVO> page = setmealMapper.page(setmealPageQueryDTO);


	return new PageResult(page.getTotal(), page.getResult());
    }

    public SetmealVO getById(Long id) {
	SetmealVO setmealVO = setmealMapper.getById(id);
	List<SetmealDish> setmealDish = dishSetmealMapper.getById(id);
	setmealVO.setSetmealDishes(setmealDish);

	return setmealVO;
    }

    @Transactional
    public void delete(Long[] ids) {
	for (Long id : ids) {
	    SetmealVO setmeal = setmealMapper.getById(id);
	    if(StatusConstant.ENABLE.equals(setmeal.getStatus())){
		throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
	    }
	}
	
	setmealMapper.delete(ids);
	dishSetmealMapper.delete(ids);
    }

    public void startOrStop(Integer status, Long id) {
	//起售套餐时，判断套餐内是否有停售菜品，有停售菜品提示"套餐内包含未启售菜品，无法启售"
	if(status.equals(StatusConstant.ENABLE)){
	    List<Dish> dishList = dishMapper.getBySetmealId(id);
	    if(dishList != null && !dishList.isEmpty()){
		dishList.forEach(dish -> {
		    if(StatusConstant.DISABLE.equals(dish.getStatus())){
			throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
		    }
		});
	    }
	}
	
	Setmeal setmeal = Setmeal.builder().id(id).status(status).build();
	setmealMapper.update(setmeal);
    }

    @Transactional
    public void update(SetmealDTO setmealDto) {
	Setmeal setmeal = new Setmeal();
	BeanUtils.copyProperties(setmealDto, setmeal);

	setmealMapper.update(setmeal);

	Long id = setmeal.getId();

	List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
	for (SetmealDish setmealDish : setmealDishes) {
	    setmealDish.setSetmealId(id);
	}

	dishSetmealMapper.delete(new Long[]{id});
	dishSetmealMapper.addDishSetmeal(setmealDishes);
    }
}