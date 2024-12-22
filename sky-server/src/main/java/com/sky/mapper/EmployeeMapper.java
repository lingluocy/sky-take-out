package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    // 根据用户名查询员工
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);
    
    //添加员工
    @AutoFill(OperationType.INSERT)
    void addEmp(Employee employee);

    //根据id查询员工
    @Select("select * from employee where id = #{id}")
     Employee getById(String id);

    //员工分页查询
    Page<Employee> page(EmployeePageQueryDTO employeePageQueryDTO);

    //更新员工信息
    @AutoFill(OperationType.UPDATE)
    void update(Employee employee);
}