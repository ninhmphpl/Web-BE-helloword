package com.spring.web.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.web.model.*;
import com.spring.web.repository.AddressRepository;
import com.spring.web.repository.PictureRepository;
import com.spring.web.repository.SellerRepository;
import com.spring.web.repository.UserRepository;
import com.spring.web.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional // Khi lỗi thì không được lưu gì vào DataBase
public class SellerService implements ISellerService {
    @Autowired
    private SellerRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Override
    public Object create(Seller seller, String username, String password) {
        Iterable<Seller> sellers = repository.findAll();
        for (Seller seller1 : sellers) {
            if (seller1.getUser().getUsername().equals(username)) {
                return "303, User Đã Bị Trùng";
            }
        }
        seller.setId(null);
        User user1 = new User();
        user1.setRole(new Role(1L, null));
        user1.setStatus(new Status(1L, null, null));
        user1.setId(null);
        user1.setUsername(username);
        user1.setPassword(passwordEncoder.encode(password));
        user1 = userRepository.save(user1);
        Address address1 = addressRepository.save(seller.getAddress());
        seller.setAddress(address1);
        seller.setUser(user1);
        seller.setDescription(" Không có thông tin");
        return repository.save(seller);
    }

    @Override
    public Page<Seller> findAllSellerPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Seller findByProductDetailContaining(ProductDetail productDetail) {
        return repository.findByListProductContaining(productDetail);
    }


    @Override
    public List<Seller> finaAllSellerByNameContaining(String name) {
        List<Seller> sellerList = repository.findAllByNameContaining(name);
        return sellerList;
    }

}

