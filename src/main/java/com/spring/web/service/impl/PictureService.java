package com.spring.web.service.impl;

import com.spring.web.model.Picture;
import com.spring.web.model.ProductDetail;
import com.spring.web.model.Status;
import com.spring.web.repository.PictureRepository;
import com.spring.web.service.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class PictureService implements IPictureService {
    @Autowired
    private PictureRepository repository;
    @Autowired
    private ProductDetailService productDetailService;

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

    @Override
    public Picture updatePicture(Picture picture) {
        Optional<Picture> picture1 = findById(picture.getId());
        if (picture1.isPresent()) {
            Picture result = picture1.get();
            result.setName(picture.getName());
            return repository.save(result);
        }
        return null;
    }

}
