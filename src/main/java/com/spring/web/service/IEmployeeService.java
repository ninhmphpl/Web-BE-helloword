package com.spring.web.service;

import com.spring.web.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmployeeService extends CRUDService<Employee , Long> {
    Page<Employee> findAllPage(Pageable pageable);
    Employee createEmployee(Employee employee);
}
