package com.spring.web.service.impl;

import com.spring.web.model.District;
import com.spring.web.service.IDistrictService;

import java.util.List;
import java.util.Optional;

public class DistrictService implements IDistrictService {
    @Override
    public Optional<District> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<District> findAll() {
        return null;
    }

    @Override
    public District save(District district) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
