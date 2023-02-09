package com.spring.web.service.impl;

import com.spring.web.model.Position;
import com.spring.web.repository.PositionRepository;
import com.spring.web.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class PositionService implements IPositionService {
    @Autowired
    private PositionRepository repository;
    @Override
    public Optional<Position> findById(Long aLong) {
        return repository.findById();
    }

    @Override
    public List<Position> findAll() {
        return repository.findAll();
    }

    @Override
    public Position save(Position position) {
        return repository.save(position);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);

    }
}
