package com.spring.web.service.impl;

import com.spring.web.model.Status;
import com.spring.web.repository.StatusRepository;
import com.spring.web.service.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class StatusService implements IStatusService {
    @Autowired
    private StatusRepository repository;
    @Override
    public Optional<Status> findById(Long aLong) {
        return repository.findById();
    }

    @Override
    public List<Status> findAll() {
        return repository.findAll();
    }

    @Override
    public Status save(Status status) {
        return repository.save(status);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);

    }
}
