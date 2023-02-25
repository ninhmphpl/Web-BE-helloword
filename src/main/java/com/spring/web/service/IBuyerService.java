package com.spring.web.service;

import com.spring.web.model.Bill;
import com.spring.web.model.Buyer;

import java.util.List;
import java.util.Optional;

public interface IBuyerService extends CRUDService<Buyer , Long>{
    Object createBuyer(Buyer buyer, String username, String password);
    Optional<Buyer> getBuyer();
    boolean changePassword(String oldPassword, String newPassword, Buyer buyer);
}
