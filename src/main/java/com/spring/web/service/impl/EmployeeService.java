package com.spring.web.service.impl;

import com.spring.web.model.Employee;
import com.spring.web.repository.EmployeeRepository;
import com.spring.web.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private EmployeeRepository repository;

    @Override
    public Optional<Employee> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public List<Employee> findAll() {
        return repository.findAll();
    }

    @Override
    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
    public Page<Employee> findAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

}
