package com.itheima.controller;

import com.itheima.domain.Employee;
import com.itheima.domain.Result;
import com.itheima.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping("/emps")
    public Result<List<Employee>> list() {
        return Result.success(employeeMapper.selectList(null));
    }
}
