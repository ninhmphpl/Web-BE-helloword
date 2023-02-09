package com.spring.web.service.impl;

import com.spring.web.model.Admin;
import com.spring.web.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class AdminService implements IAdminService {
    @Autowired
    private asdfsRepository repository;

    @Override
    public Optional<Admin> findById(Long aLong) {

        return repository.findById();
    }

    @Override
    public List<Admin> findAll() {

        return repository.findAll();
    }

    @Override
    public Admin save(Admin admin) {

        return repository.save(admin);
    }

    @Override
    public void delete(Long aLong) {
        repository.save(aLong)

    }
}
