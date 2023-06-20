package com.cprince.example.springbootsecurityemployeestarter.dao;

import com.cprince.example.springbootsecurityemployeestarter.entity.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> findAll();
    Employee findById(int employeeId);
    Employee save(Employee employee);
    void delete(int employeeId);
}
