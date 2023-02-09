package com.spring.web.service.impl;

import com.spring.web.model.Position;
import com.spring.web.service.IPositionService;

import java.util.List;
import java.util.Optional;

public class PositionService implements IPositionService {
    @Override
    public Optional<Position> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Position> findAll() {
        return null;
    }

    @Override
    public Position save(Position position) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
