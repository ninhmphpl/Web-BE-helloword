package com.spring.web.service.impl;

import com.spring.web.model.Buyer;
import com.spring.web.service.IBuyerService;

import java.util.List;
import java.util.Optional;

public class BuyerService implements IBuyerService {
    @Override
    public Optional<Buyer> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Buyer> findAll() {
        return null;
    }

    @Override
    public Buyer save(Buyer buyer) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
