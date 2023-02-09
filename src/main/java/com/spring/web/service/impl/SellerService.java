package com.spring.web.service.impl;

import com.spring.web.model.Seller;
import com.spring.web.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class SellerService implements ISellerService {
    @Autowired
    private asdfsRepository repository;
    @Override
    public Optional<Seller> findById(Long aLong) {
        return repository.findById();
    }

    @Override
    public List<Seller> findAll() {
        return repository.findAll();
    }

    @Override
    public Seller save(Seller seller) {
        return repository.save(seller);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);

    }
}
