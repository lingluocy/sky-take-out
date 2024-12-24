package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐管理相关接口")
public class SetmealController {
    @Autowired
    SetmealService setmealService;
    
    @PostMapping
    @ApiOperation("添加套餐")
    public Result addSetmeal(@RequestBody SetmealDTO setmealDto) {
	log.info("添加套餐:{}", setmealDto);
	setmealService.addSetmeal(setmealDto);
	return Result.success();
    }
    
    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
	log.info("套餐分页查询:{}}", setmealPageQueryDTO);
	PageResult pageResult = setmealService.page(setmealPageQueryDTO);
	return Result.success(pageResult);
    }
}
/*
  todo 套餐相关接口
  修改套餐
  套餐起售、停售
  批量删除套餐
  根据id查询套餐
 */