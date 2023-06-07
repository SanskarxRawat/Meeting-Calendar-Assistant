package com.meeting.calendar.controller;


import com.meeting.calendar.entity.Employee;
import com.meeting.calendar.exceptions.EmployeeNotFoundException;
import com.meeting.calendar.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/employee")
@Tag(name = "Employee Controller",description = "Controller for managing employees and related operations.")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @PostMapping("/create")
    @Operation(summary = "This api is used to create employee.")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        log.info("Employee to Create : {}",employee);
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @GetMapping("/{name}")
    @Operation(summary = "This api is used to get the employee.")
    public ResponseEntity<Employee> getEmployeeByName(@PathVariable String name) {
        log.info("Finding the employee with name: {}",name);
        Employee employee = employeeService.getEmployeeByName(name);
        if (Objects.isNull(employee)) {
            throw new EmployeeNotFoundException("Employee not found with name: " + name);
        }

        return ResponseEntity.ok(employee);
    }
}
