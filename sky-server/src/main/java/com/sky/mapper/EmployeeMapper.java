package com.sky.mapper;

import java.util.List;

import com.sky.entity.Employee;

import io.lettuce.core.dynamic.annotation.Param;

public interface EmployeeMapper {
	public Employee getByUsername(String username);

	public List<Employee> page(@Param("name") String name);

	public void insert(Employee employee);

	public Employee getById(Long id);

	public void update(Employee employee);
}
