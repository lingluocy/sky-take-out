package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    //员工登录
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
	String username = employeeLoginDTO.getUsername();
	String password = employeeLoginDTO.getPassword();

	//1、根据用户名查询数据库中的数据
	Employee employee = employeeMapper.getByUsername(username);

	//2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
	if (employee == null) {
	    //账号不存在
	    throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
	}

	//密码比对
	password = DigestUtils.md5DigestAsHex(password.getBytes());
	if (!password.equals(employee.getPassword())) {
	    //密码错误
	    throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
	}

	if (employee.getStatus().equals(StatusConstant.DISABLE)) {
	    //账号被锁定
	    throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
	}

	//3、返回实体对象
	return employee;
    }

    //添加员工
    public void addEmp(EmployeeDTO employeeDTO) {
	Employee employee = new Employee();

	//属性拷贝
	BeanUtils.copyProperties(employeeDTO, employee);

	//设置状态(默认启用)
	employee.setStatus(StatusConstant.ENABLE);

	//设置默认密码(md5加密)
	employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

	//设置创建时间和更新时间
	employee.setCreateTime(LocalDateTime.now());
	employee.setUpdateTime(LocalDateTime.now());

	//设置创建人和修改人
	employee.setCreateUser(BaseContext.getCurrentId());
	employee.setUpdateUser(BaseContext.getCurrentId());

	employeeMapper.addEmp(employee);
    }

    //根据id查询员工
    @Override
    public Employee getById(String id) {
	return employeeMapper.getById(id);
    }

    //员工分页查询 
    @Override
    public PageResult page(EmployeePageQueryDTO employeePageQueryDTO) {
	//使用pagehelper 进行分页查询
	PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
	Page<Employee> page = employeeMapper.page(employeePageQueryDTO);

	long total = page.getTotal();
	List<Employee> list = page.getResult();

	return new PageResult(total, list);
    }

    //启用禁用员工
    @Override
    public void startOrStop(Integer status, Long id) {
	Employee employee = Employee.builder()
		.id(id)
		.status(status)
		.updateTime(LocalDateTime.now())
		.updateUser(BaseContext.getCurrentId())
		.build();

	employeeMapper.update(employee);
    }

    //修改员工信息
    @Override
    public void updateEmp(EmployeeDTO employeeDTO) {
	Employee employee = Employee.builder()
		.updateTime(LocalDateTime.now())
		.updateUser(BaseContext.getCurrentId())
		.build();

	BeanUtils.copyProperties(employeeDTO, employee);

	employeeMapper.update(employee);
    }

}