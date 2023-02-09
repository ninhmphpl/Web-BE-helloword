package com.spring.web.service.impl;

import com.spring.web.model.Seller;
import com.spring.web.service.ISellerService;

import java.util.List;
import java.util.Optional;

public class SellerService implements ISellerService {
    @Override
    public Optional<Seller> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }

    @Override
    public Seller save(Seller seller) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
