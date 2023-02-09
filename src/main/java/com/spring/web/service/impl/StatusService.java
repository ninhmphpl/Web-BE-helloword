package com.spring.web.service.impl;

import com.spring.web.model.Status;
import com.spring.web.service.IStatusService;

import java.util.List;
import java.util.Optional;

public class StatusService implements IStatusService {
    @Override
    public Optional<Status> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Status> findAll() {
        return null;
    }

    @Override
    public Status save(Status status) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
