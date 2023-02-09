package com.spring.web.repository;

import com.spring.web.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findEmployeeByName(String name);
}
