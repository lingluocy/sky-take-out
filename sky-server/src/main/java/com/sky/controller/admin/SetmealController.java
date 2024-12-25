package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
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

    @GetMapping("{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id) {
	log.info("根据id查询套餐:{}", id);
	SetmealVO setmealVO = setmealService.getById(id);
	return Result.success(setmealVO);
    }

    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result delete(Long[] ids) {
	log.info("批量删除套餐:{}", ids);
	setmealService.delete(ids);
	return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售停售")
    public Result startOrStop(@PathVariable Integer status, Long id) {
	log.info("套餐起售停售:{},{}", status, id);
	setmealService.startOrStop(status, id);
	return Result.success();
    }

    @PutMapping
    @ApiOperation("修改套餐")
    public Result update(@RequestBody SetmealDTO setmealDto) {
	log.info("修改套餐:{}", setmealDto);
	setmealService.update(setmealDto);
	return Result.success();
    }
}