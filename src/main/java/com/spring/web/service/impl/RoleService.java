package com.spring.web.service.impl;

import com.spring.web.model.Role;
import com.spring.web.service.IRoleService;

import java.util.List;
import java.util.Optional;

public class RoleService implements IRoleService {
    @Override
    public Optional<Role> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public Role save(Role role) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
