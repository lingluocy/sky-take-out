package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    DishMapper dishMapper;
    @Autowired
    DishFlavorMapper dishFlavorMapper;

    @Override
    @Transactional
    public void addWithFlavor(DishDTO dishDTO) {
	log.info("添加菜品");
	Dish dish = new Dish();
	BeanUtils.copyProperties(dishDTO, dish);
	dishMapper.add(dish);

	Long id = dish.getId();

	log.info("添加口味");
	List<DishFlavor> flavors = dishDTO.getFlavors();
	if (flavors != null && !flavors.isEmpty()) {
	    dishFlavorMapper.addBatch(flavors, id);
	}
    }

    @Override
    public PageResult page(DishPageQueryDTO queryDTO) {
	PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
	Page<DishVO> page = dishMapper.page(queryDTO);

	return new PageResult(page.getTotal(), page.getResult());
    }

    public void updateStatus(Integer status, Long id) {
	Dish dish = new Dish();
	dish.setId(id);
	dish.setStatus(status);

	dishMapper.update(dish);
    }

    public void update(DishDTO dishDTO) {
	Dish dish = new Dish();
	BeanUtils.copyProperties(dishDTO, dish);

	dishMapper.update(dish);

	Long dishId = dish.getId();

	List<DishFlavor> flavors = dishDTO.getFlavors();
	if (flavors != null && !flavors.isEmpty()) {
	    for (DishFlavor flavor : flavors) {
		flavor.setId(dishId);
		dishFlavorMapper.update(flavor);
	    }
	}
    }

    @Override
    public DishVO getById(Long id) {
	Dish dish = dishMapper.getById(id);
	List<DishFlavor> dishFlavor = dishFlavorMapper.getById(id);

	DishVO dishVO = new DishVO();
	BeanUtils.copyProperties(dish, dishVO);
	dishVO.setFlavors(dishFlavor);

	return dishVO;
    }
}