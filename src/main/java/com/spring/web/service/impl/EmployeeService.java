package com.spring.web.service.impl;

import com.spring.web.model.Employee;
import com.spring.web.model.EmployeeRender;
import com.spring.web.repository.EmployeeRepository;
import com.spring.web.repository.GenderRepository;
import com.spring.web.repository.PositionRepository;
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
    @Autowired
    private GenderRepository genderRepository;
    @Autowired
    private PositionRepository positionRepository;

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



    public List<Employee> findAllByNameEmployee(String name) {
        return repository.findEmployeeByNameContaining(name);
    }
    public EmployeeRender render(Employee employee){
        return new EmployeeRender(repository.findAll(),
                genderRepository.findAll(), positionRepository.findAll(),
                employee);
    }
}
