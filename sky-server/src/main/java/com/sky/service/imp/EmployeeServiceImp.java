package com.sky.service.imp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.sky.entity.Employee;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;

public class EmployeeServiceImp implements EmployeeService {
	@Autowired
    private EmployeeMapper employeeMapper;
	
	@Override
	public Map<String, Object> login(String username, String password) {
		//根据用户名查询数据库
        Employee emp = employeeMapper.getByUsername(username);
        //用户不存在
        if (emp == null) {
            return null;
        }
        //密码比对
        if ( !emp.getPassword().equals(password) ) {
            return null;
        }
        //登录成功，封装返回数据
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id", emp.getId());
        dataMap.put("username", emp.getUsername());
        dataMap.put("name", emp.getName());
        dataMap.put("token", "mock-token-00001");
        return dataMap;
	}

	@Override
	public List<Employee> page(String name) {
		return employeeMapper.page(name);
	}

	@Override
	public void add(Employee employee) {
		employee.setStatus(1);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.insert(employee);
	}

	@Override
	public Employee getById(Long id) {
		return employeeMapper.getById(id);
	}

	@Override
	public void update(Employee employee) {
		employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.update(employee);
	}
	
}
