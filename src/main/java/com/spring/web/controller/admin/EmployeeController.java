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
import org.springframework.expression.ExpressionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
    @Autowired
    private EmployeeService employeeServices;

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
        employee.setStatus(new Status(status,null));
        return new ResponseEntity<>(employeeService.save(employee),HttpStatus.OK);
    }
    // Phương thức tìm kiếm nhân viên
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> findSearch (@RequestParam("search") String search){
        return new ResponseEntity<>(employeeServices.findAllByNameEmployee(search), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> save (@RequestBody Employee employee){
        employeeService.save(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("employees")
    public Employee createEmployee (@Valid @RequestBody Employee employee){
        return employeeRepository.save(employee);
    }
    // XOá thẳng nhân viên khỏi base
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee (@PathVariable Long id){
        employeeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //    @GetMapping("/{id}")
//    public ResponseEntity<?> renderUpdate(@PathVariable Long id){
//        Employee employee = employeeService.findById(id).get();
//        EmployeeRender employeeRender = employeeService.render(employee);
//        return new ResponseEntity<>(employeeRender, HttpStatus.OK);
//    }
    @PutMapping
    public ResponseEntity<?> update (@RequestBody Employee employee){
        employeeService.save(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    // Phương thức update Nhân Viên ( đang giai đoạn kiểm thử)
    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployee (@PathVariable(value = "id") Long id,
                                                    @Valid @RequestBody Employee employee) throws Exception {
        Employee employees = employeeRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Nhân viên này không tồn tại: " + id));

        employee.setAge(employee.getAge());
        employee.setName(employee.getName());
        employee.setBirth(employee.getBirth());
        employee.setId(employee.getId());
        employee.setGender(employee.getGender());
        employee.setStatus(employee.getStatus());
        employee.setPhoneNumber(employee.getPhoneNumber());
        employee.setPosition(employee.getPosition());
        employee.setUser(employee.getUser());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }
}

