package com.spring.web.service.impl;

import com.spring.web.model.Employee;
import com.spring.web.model.EmployeeRender;
import com.spring.web.model.User;
import com.spring.web.repository.EmployeeRepository;
import com.spring.web.repository.GenderRepository;
import com.spring.web.repository.PositionRepository;
import com.spring.web.repository.UserRepository;
import com.spring.web.service.IEmployeeService;
import com.spring.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private UserRepository userRepository;


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
    @Override
    public Employee createEmployee(Employee employee) {
        employee.setId(null);
        employee.getUser().setId(null);
        User user = userRepository.save(employee.getUser());
        employee.setUser(user);
        LocalDate localDate =employee.getBirth();
        int age = calculateAge(localDate);
        System.out.println(age);
        employee.setAge(age);
        Employee result = repository.save(employee);
        return result;
    }
    public int calculateAge(
            LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public Page <Employee> findAllByNameEmployee(String name,Pageable pageable) {
        return repository.findEmployeeByNameContaining(name,pageable);
    }
//    public EmployeeRender render(Employee employee){
//        return new EmployeeRender(repository.findAll(),
//                genderRepository.findAll(), positionRepository.findAll(),
//                employee);
//    }
}
