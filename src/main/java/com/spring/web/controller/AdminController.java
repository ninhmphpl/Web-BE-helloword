package com.spring.web.controller;

import com.spring.web.model.Employee;
import com.spring.web.service.impl.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping("/admEmployee")
public class AdminController {
    @Autowired
    EmployeeService employeeService;
    @GetMapping
    public ResponseEntity<List<Employee>>findAllEmployee(){
        return new ResponseEntity<>(employeeService.findAll() , HttpStatus.OK);
    }
    @GetMapping("admEmployeeByName")
    public ResponseEntity<?> findByNameEmployee(@RequestParam("admEmployeeByName") String admEmployeeByName){
        return new ResponseEntity<>(employeeService.findEmployeeByName(admEmployeeByName) , HttpStatus.OK);
    }
}
