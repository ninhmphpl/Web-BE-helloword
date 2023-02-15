package com.spring.web.controller.admin;

import com.spring.web.model.Employee;
import com.spring.web.model.Position;
import com.spring.web.model.Status;
import com.spring.web.repository.EmployeeRepository;
import com.spring.web.service.IEmployeeService;
import com.spring.web.service.impl.EmployeeService;
import com.spring.web.service.impl.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")


public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private EmployeeRepository employeeRepository;

    // Hiển thị toàn bộ emplooyee và phân trang
    @GetMapping("/employee-list")
    public ResponseEntity<?> findAllPage(@PageableDefault(value = 5)
                                         @SortDefault(sort = "id", direction = DESC)
                                         Pageable pageable) {

        Page<Employee> page = employeeService.findAllPage(pageable);
        System.out.println(page);

        if (pageable.getPageNumber() >= page.getTotalPages() || pageable.getPageNumber() < 0) {
            System.out.println("Page Number out range page");
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    // Phương thức thay đổi chức vụ cho nhân viên
    @PutMapping("/position-update/{id}/{position}")
    public ResponseEntity<?> updatePosition(@PathVariable("id") Long id, @PathVariable("position") Long position) {
        Employee employee = employeeService.findById(id).get();
        employee.setPosition(new Position(position, null, null));
        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.OK);
    }
//    Phương thức xóa mềm nhân viên
    @PutMapping("/delete-employee/{id}/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long id,@PathVariable("status") Long status) {
        Employee employee = employeeService.findById(id).get();
        employee.setStatus(new Status(status,null,null));
        return new ResponseEntity<>(employeeService.save(employee),HttpStatus.OK);
    }
}
