package com.spring.web.service.impl;

import com.spring.web.model.District;
import com.spring.web.model.Province;
import com.spring.web.repository.DistricRepository;
import com.spring.web.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class DistrictService implements IDistrictService {
    @Autowired
    private DistricRepository repository;
    @Override
    public Optional<District> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public List<District> findAll() {

        return repository.findAll();
    }

    @Override
    public District save(District district) {

        return repository.save(district);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);

    }

    public List<District> findAllDistrictbyProvince(Province province){
     return repository.findDistinctByProvince(province);

    }
}
