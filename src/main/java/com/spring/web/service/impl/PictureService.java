package com.spring.web.service.impl;

import com.spring.web.model.Picture;
import com.spring.web.repository.PictureRepository;
import com.spring.web.service.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PictureService implements IPictureService {
    @Autowired
    private PictureRepository repository;

    @Override
    public Optional<Picture> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public List<Picture> findAll() {
        return repository.findAll();
    }

    @Override
    public Picture save(Picture picture) {
        return repository.save(picture);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);
    }
}
