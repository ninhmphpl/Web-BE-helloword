package com.spring.web.repository;

import com.spring.web.model.Employee;
import com.spring.web.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
   Page<Employee> findEmployeeByNameContaining(String name, Pageable pageable);
   Optional<Employee> findByUser(User user);
}
