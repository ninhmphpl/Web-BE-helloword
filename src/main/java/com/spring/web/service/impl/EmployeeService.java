package com.spring.web.service.impl;

import com.spring.web.model.Employee;
import com.spring.web.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private asdfsRepository repository;
    @Override
    public Optional<Employee> findById(Long aLong) {
        return repository.findById();
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
}
