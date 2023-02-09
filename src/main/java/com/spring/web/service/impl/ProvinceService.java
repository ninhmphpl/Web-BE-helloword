package com.spring.web.service.impl;

import com.spring.web.model.Province;
import com.spring.web.repository.ProvinceRepository;
import com.spring.web.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class ProvinceService implements IProvinceService {
    @Autowired
    private ProvinceRepository repository;
    @Override
    public Optional<Province> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public List<Province> findAll() {
        return repository.findAll();
    }

    @Override
    public Province save(Province province) {
        return repository.save(province);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);

    }
}
