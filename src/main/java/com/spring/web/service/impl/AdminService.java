package com.spring.web.service.impl;

import com.spring.web.model.Admin;
import com.spring.web.service.IAdminService;

import java.util.List;
import java.util.Optional;

public class AdminService implements IAdminService {
    @Override
    public Optional<Admin> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Admin> findAll() {
        return null;
    }

    @Override
    public Admin save(Admin admin) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
