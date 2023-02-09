package com.spring.web.service.impl;

import com.spring.web.model.Role;
import com.spring.web.repository.RoleRepository;
import com.spring.web.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository repository;
    @Override
    public Optional<Role> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);

    }
}
