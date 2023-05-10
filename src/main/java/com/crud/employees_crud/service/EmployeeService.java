package com.crud.employees_crud.service;

import java.util.List;

import com.crud.employees_crud.entity.Employee;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee getEmployee(int id);

    Employee save(Employee employee);

    void deleteById(int id);
}
