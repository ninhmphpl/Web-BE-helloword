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
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductDetailService productSimple;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private PictureRepository pictureRepository;
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

    public Object create(Seller seller) {
        Iterable<Seller> hihi = repository.findAll();
        for (
               Seller seller1 : hihi
       )
        if (seller1.getUser().getUsername().equals(seller.getUser().getUsername())) {
            return "303, User Đã Bị Trùng";
        }
        seller.setId(null);
        User user1 = seller.getUser();
        user1.setRole(new Role(1L, null));
        user1.setStatus(new Status(1L, null, null));
        user1.setId(null);
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
        List<Seller> sellerList =  repository.findAllByNameContaining(name);
        return  sellerList;
    }

}

