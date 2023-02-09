package com.spring.web.service.impl;

import com.spring.web.model.Buyer;
import com.spring.web.repository.BuyerRepository;
import com.spring.web.service.IBuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class BuyerService implements IBuyerService {
    @Autowired
    private BuyerRepository repository;
    @Override
    public Optional<Buyer> findById(Long aLong) {

        return repository.findById(aLong);
    }

    @Override
    public List<Buyer> findAll() {

        return repository.findAll();
    }

    @Override
    public Buyer save(Buyer buyer) {

        return repository.save(buyer);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);

    }
}
