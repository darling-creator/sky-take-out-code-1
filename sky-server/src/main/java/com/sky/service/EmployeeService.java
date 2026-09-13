package com.sky.service;

import java.util.List;
import java.util.Map;

import com.sky.entity.Employee;

public interface EmployeeService {
    public Map<String,Object> login(String username, String password);
    
    public List<Employee> page(String name);

    public void add(Employee employee);

    public Employee getById(Long id);

    public void update(Employee employee);
}
