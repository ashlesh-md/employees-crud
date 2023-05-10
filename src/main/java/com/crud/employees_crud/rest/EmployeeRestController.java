package com.crud.employees_crud.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.crud.employees_crud.entity.Employee;
import com.crud.employees_crud.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    EmployeeService employeeService;
    Map<String, String> allApiList = Map.of(
            "getAllEmployeeInformation", "http://localhost:8080/api/employees",
            "getAllEmployeeInformationById", "http://localhost:8080/api/employees/{id}");

    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/**")
    public List<Employee> getErrorResponse() {
        throw new RuntimeException("Bad Request");
    }

    @GetMapping("")
    public Map<String, String> getAllApiInformtion() {
        return allApiList;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getAllEmployee(@PathVariable int employeeId) {
        try {
            return employeeService.getEmployee(employeeId);

        } catch (Exception e) {
            throw new RuntimeException("Employee Not Found of Id : " + employeeId);
        }
    }

    @DeleteMapping("/employees/delete/{employeeId}")
    public ResponseEntity<EmployeeErrorResponse> deleteEmployeeById(@PathVariable int employeeId) {
        if (employeeId > 5 || employeeId < 1) {
            throw new EmployeeNotFoundException("Invalid Employee Id : " + employeeId);
        }
        employeeService.deleteById(employeeId);
        EmployeeErrorResponse response = new EmployeeErrorResponse();
        response.setMessage("Successfully deleted employee of id : " + employeeId);
        response.setStatus(HttpStatus.OK.value());
        response.setTimestamp(System.currentTimeMillis());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        // If user adds id in the field...It will force to auto generate it.
        employee.setId(0);
        return employeeService.save(employee);
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> handleException(EmployeeNotFoundException exception) {
        EmployeeErrorResponse response = new EmployeeErrorResponse();
        response.setMessage(exception.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> handleException(RuntimeException exception) {
        EmployeeErrorResponse response = new EmployeeErrorResponse();
        response.setMessage(exception.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
