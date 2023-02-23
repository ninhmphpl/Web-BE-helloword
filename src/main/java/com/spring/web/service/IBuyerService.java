package com.spring.web.service;

import com.spring.web.model.Buyer;

import java.util.Optional;

public interface IBuyerService extends CRUDService<Buyer , Long>{
    Object createBuyer(Buyer buyer, String username, String password);
    public Optional<Buyer> getBuyer();
}
