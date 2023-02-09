package com.spring.web.service.impl;

import com.spring.web.model.Ward;
import com.spring.web.service.IWardService;

import java.util.List;
import java.util.Optional;

public class WardService implements IWardService {
    @Override
    public Optional<Ward> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Ward> findAll() {
        return null;
    }

    @Override
    public Ward save(Ward ward) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
