package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {


    //员工登录
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    //添加员工
    void addEmp(EmployeeDTO employeeDTO);

    Employee selectById(String id);

    PageResult page(EmployeePageQueryDTO employeePageQueryDTO);
}