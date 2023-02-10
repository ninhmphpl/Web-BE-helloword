package com.spring.web.controller.admin;

import com.spring.web.model.Employee;
import com.spring.web.model.EmployeeRender;
import com.spring.web.repository.EmployeeRepository;
import com.spring.web.service.impl.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")


public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;
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
    @GetMapping("/{id}")
    public ResponseEntity<?> renderUpdate(@PathVariable Long id){
        Employee employee = employeeService.findById(id).get();
        EmployeeRender employeeRender = employeeService.render(employee);
        return new ResponseEntity<>(employeeRender, HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Employee employee){
        employeeService.save(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/new")
    public ResponseEntity<EmployeeRender> employeeRenderResponseEntity(){
        EmployeeRender employeeRender = employeeService.render(null);
        return new ResponseEntity<>(employeeRender, HttpStatus.OK);
    }



}
