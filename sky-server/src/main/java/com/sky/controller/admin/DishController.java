package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
public class DishController {
    @Autowired
    DishService dishService;
    
    @PostMapping
    @ApiOperation("添加菜品")
    public Result add(@RequestBody DishDTO dishDTO){
	log.info("添加菜品:{}",dishDTO);
	dishService.addWithFlavor(dishDTO);
	return Result.success();
    }
    
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO queryDTO){
	log.info("菜品分页查询:{}",queryDTO);
	PageResult page = dishService.page(queryDTO);
	return Result.success(page);
    }
    
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id){
	log.info("根据id查询菜品:{}",id);
	DishVO dishVO = dishService.getById(id);
	return Result.success(dishVO);
    }
    
    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售停售")
    public Result updateStatus(@PathVariable Integer status,Long id){
	log.info("菜品起售停售 id: {},status:{}",id,status);
	dishService.updateStatus(status,id);
	return Result.success();
    }
    
    @PutMapping
    @ApiOperation("菜品修改")
    public Result update(@RequestBody DishDTO dishDTO){
	log.info("菜品修改:{}",dishDTO);
	dishService.update(dishDTO);
	return Result.success();
    }
}