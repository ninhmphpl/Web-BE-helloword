package com.spring.web.service.impl;

import com.spring.web.model.Street;
import com.spring.web.service.IStreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class StreetService implements IStreetService {
    @Autowired
    private asdfsRepository repository;
    @Override
    public Optional<Street> findById(Long aLong) {
        return repository.findById();
    }

    @Override
    public List<Street> findAll() {
        return repository.findAll();
    }

    @Override
    public Street save(Street street) {
        return repository.save(street);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);

    }
}
