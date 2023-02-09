package com.spring.web.service.impl;

import com.spring.web.model.Province;
import com.spring.web.service.IProvinceService;

import java.util.List;
import java.util.Optional;

public class ProvinceService implements IProvinceService {
    @Override
    public Optional<Province> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Province> findAll() {
        return null;
    }

    @Override
    public Province save(Province province) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
