package com.spring.web.controller;

import com.spring.web.model.Employee;
import com.spring.web.repository.EmployeeRepository;
import com.spring.web.service.impl.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping("/list-employee")
public class AdminController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<List<Employee>>listEmployee(){
        return new ResponseEntity<>(employeeService.findAll() , HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> findSearch(@RequestParam("search") String search){
        return new ResponseEntity<>(employeeService.findAllByNameEmployee(search) , HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Employee employee){
        employeeService.save(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        employeeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Employee employee){
        employeeService.save(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
