package com.spring.web.controller.admin;

import com.spring.web.model.*;
import com.spring.web.repository.EmployeeRepository;
import com.spring.web.service.IEmployeeService;
import com.spring.web.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/employeeRender")


public class EmployeeRenderController {
    @Autowired
    private PositionService positionService;
    @Autowired
    private GenderService gendersService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private RoleService roleService;

    @GetMapping("all-employee-render")
    public ResponseEntity<?> findAllEmployeeRender() {
        List<Gender> genders= gendersService.findAll();
        List <Position> positions= positionService.findAll();
        List <Status> statuses= statusService.findAll();
        List <Role> roles= roleService.findAll();
        return new ResponseEntity<>(new EmployeeRender(genders,positions,statuses,roles), HttpStatus.OK);
    }
}
