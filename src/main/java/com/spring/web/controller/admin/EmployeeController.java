package com.spring.web.controller.admin;

import com.spring.web.model.Employee;
import com.spring.web.service.impl.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")


public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee-list")
    public ResponseEntity<?> findAllPage(@PageableDefault(value = 5)
                                         @SortDefault(sort = "id", direction = DESC)
                                         Pageable pageable){

        Page<Employee> page = employeeService.findAllPage(pageable);
        System.out.println(page);

        if(pageable.getPageNumber() >= page.getTotalPages() || pageable.getPageNumber() < 0){
            System.out.println("Page Number out range page");
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        return new ResponseEntity<>(page , HttpStatus.OK);
    }
}
