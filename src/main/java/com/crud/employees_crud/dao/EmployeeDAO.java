package com.crud.employees_crud.dao;

import java.util.List;

import com.crud.employees_crud.entity.Employee;

public interface EmployeeDAO {
    List<Employee> getAllEmployees();

    Employee getEmployee(int id);

    Employee save(Employee employee);

    void deleteById(int id);
}
