package com.spring.web.controller.admin;

import com.spring.web.model.Employee;
import com.spring.web.repository.EmployeeRepository;
import com.spring.web.service.adminActive.EmployeeDisableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userDisable")
@CrossOrigin("*")
@PropertySource("classpath:application.properties")
public class EmployeeDisableController {
    @Autowired
    EmployeeDisableService admDisableService;
    @Autowired
    EmployeeRepository employeeRepository;
    @PutMapping("/{id}/{status}")
    public ResponseEntity<?> activeBlockUser(@PathVariable Long id, @PathVariable Long status) {
        Employee employee = employeeRepository.findById(id).get();
        employeeRepository.save(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
