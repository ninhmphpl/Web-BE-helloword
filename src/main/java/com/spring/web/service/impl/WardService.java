package com.spring.web.service.impl;

import com.spring.web.model.District;
import com.spring.web.model.Ward;
import com.spring.web.repository.AdminRepository;
import com.spring.web.repository.WardRepository;
import com.spring.web.service.IWardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class WardService implements IWardService {
    @Autowired
    private WardRepository repository;
    @Override
    public Optional<Ward> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public List<Ward> findAll() {
        return repository.findAll();
    }

    @Override
    public Ward save(Ward ward) {
        return repository.save(ward);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);

    }

    public List<Ward>findAllWardByDistrict(District district){
        return repository.findWardsByDistrict(district);
    }
}
