package com.crud.employees_crud.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.crud.employees_crud.entity.Employee;

import jakarta.persistence.EntityManager;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    EntityManager entityManager;

    public EmployeeDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return entityManager.createQuery("FROM Employee", Employee.class).getResultList();
    }

    @Override
    public Employee getEmployee(int id) {
        return (Employee) entityManager.createQuery("FROM Employee where id=:employeeId", Employee.class)
                .setParameter("employeeId", id)
                .getSingleResult();
    }

    @Override
    public Employee save(Employee employee) {
        return entityManager.merge(employee);
    }

    @Override
    public void deleteById(int id) {
        // entityManager.createNativeQuery("DELETE FROM Employee where id=:employeeId",
        // Employee.class)
        // .setParameter("employeeId", id).executeUpdate();
        entityManager.remove(entityManager.find(Employee.class, id));
    }

}
