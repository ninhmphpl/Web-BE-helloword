package com.spring.web.service.impl;

import com.spring.web.model.Street;
import com.spring.web.service.IStreetService;

import java.util.List;
import java.util.Optional;

public class StreetService implements IStreetService {
    @Override
    public Optional<Street> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Street> findAll() {
        return null;
    }

    @Override
    public Street save(Street street) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
