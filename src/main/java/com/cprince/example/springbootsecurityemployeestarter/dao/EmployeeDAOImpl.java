package com.cprince.example.springbootsecurityemployeestarter.dao;

import com.cprince.example.springbootsecurityemployeestarter.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDao {

    private EntityManager entityManager;

    @Autowired
    public EmployeeDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public List<Employee> findAll() {
        TypedQuery<Employee> theQuery = entityManager.createQuery("FROM Employee", Employee.class);

        List<Employee> employees = theQuery.getResultList();

        return employees;
    }

    @Override
    public Employee findById(int employeeId) {
        Employee employee = entityManager.find(Employee.class, employeeId);

        return employee;
    }

    @Override
    public Employee save(Employee employee) {
        Employee theEmployee = entityManager.merge(employee);
        return theEmployee;
    }

    @Override
    public void delete(int employeeId) {
        Employee employee = entityManager.find(Employee.class, employeeId);

        entityManager.remove(employee);
    }
}
