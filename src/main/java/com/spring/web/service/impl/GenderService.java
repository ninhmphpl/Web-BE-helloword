package com.spring.web.service.impl;

import com.spring.web.model.Gender;
import com.spring.web.repository.GenderRepository;
import com.spring.web.service.IGenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class GenderService implements IGenderService {
    @Autowired
    private GenderRepository repository;
    @Override
    public Optional<Gender> findById(Long aLong) {
        return repository.findById();
    }

    @Override
    public List<Gender> findAll() {
        return repository.findAll();
    }

    @Override
    public Gender save(Gender gender) {
        return repository.save(gender);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);

    }
}
