package com.spring.web.service.impl;

import com.spring.web.model.Address;
import com.spring.web.model.Seller;
import com.spring.web.model.User;
import com.spring.web.repository.SellerRepository;
import com.spring.web.repository.UserRepository;
import com.spring.web.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class SellerService implements ISellerService {
    @Autowired
    private SellerRepository repository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<Seller> findById(Long aLong) {
        return repository.findById(aLong);
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
    public Seller findByUsername(String username) {
        return repository.findByName(username);
    }
    public boolean signUp(Seller seller) {
        seller.setUser(userRepository.findById(2l).get());
        repository.save(seller);
        return true;
    }
    public Seller saveInfoSeller(Seller seller, User user , Address address){
        Seller seller1 = repository.findSellerByUser(user);
        seller1.setUser(seller.getUser());
        seller1.setAddress(seller.getAddress());
        seller1.setId(seller.getId());
        seller1.setName(seller.getName());
        seller1.setDescription(seller.getDescription());
        seller1.setPhoneNumber(seller.getPhoneNumber());
        return repository.save(seller1);
    }

}

