package com.cprince.example.springbootsecurityemployeestarter.service;

import com.cprince.example.springbootsecurityemployeestarter.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee findById(int employeeId);
    Employee save(Employee employee);
    void delete(int employeeId);

}
