package com.spring.web.service.impl;

import com.spring.web.model.Gender;
import com.spring.web.service.IGenderService;

import java.util.List;
import java.util.Optional;

public class GenderService implements IGenderService {
    @Override
    public Optional<Gender> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Gender> findAll() {
        return null;
    }

    @Override
    public Gender save(Gender gender) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
