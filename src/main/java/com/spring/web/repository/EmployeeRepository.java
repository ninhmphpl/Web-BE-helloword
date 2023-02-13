package com.spring.web.repository;

import com.spring.web.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
   List<Employee> findEmployeeByNameContaining(String name);
}
