package com.sky.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sky.entity.Employee;
import com.sky.result.Result;
import com.sky.service.EmployeeService;

@RestController
@RequestMapping("/admin/employee")
public class EmployeeController {
	@Autowired
    private EmployeeService employeeService;
	
    @SuppressWarnings("rawtypes")
	@PostMapping("/login")
    public Result login(@RequestBody Map<String,String> map){
        //拿到网页传过来的账号密码
        String username = map.get("username");
        String password = map.get("password");

        //判断账号密码
        if("admin".equals(username) && "123456".equals(password)){
            //准备要返回给网页的数据
            Map<String,Object> data = new HashMap<>();
            data.put("id",1L);
            data.put("username","admin");
            data.put("name","管理员");
            data.put("token","mock-token-00001");
            //打包成功的结果
            return Result.success(data);
        }
        //打包失败的结果
        return Result.error("账号或密码错误");
    }
    
    //分页查询
    @GetMapping("/page")
    public Result<List<Employee>> page(@RequestParam(required = false) String name){
        List<Employee> list = employeeService.page(name);
        return Result.success(list);
    }

    //新增员工
    @PostMapping
    public Result<String> add(@RequestBody Employee employee){
        employeeService.add(employee);
        return Result.success("新增成功");
    }
    
    
    //根据id查询
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id){
        Employee emp = employeeService.getById(id);
        return Result.success(emp);
    }

    //修改员工
    @PutMapping
    public Result<String> update(@RequestBody Employee employee){
        employeeService.update(employee);
        return Result.success("修改成功");
    }
}

