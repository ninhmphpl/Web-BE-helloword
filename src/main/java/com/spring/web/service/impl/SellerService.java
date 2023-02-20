package com.spring.web.service.impl;

import com.spring.web.model.*;
import com.spring.web.repository.AddressRepository;
import com.spring.web.repository.SellerRepository;
import com.spring.web.repository.UserRepository;
import com.spring.web.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    public Seller saveInfoSeller(Seller seller, User user, Address address) {
        Seller seller1 = repository.findSellerByUser(user);
        seller1.setUser(seller.getUser());
        seller1.setAddress(seller.getAddress());
        seller1.setId(seller.getId());
        seller1.setName(seller.getName());
        seller1.setDescription(seller.getDescription());
        seller1.setPhoneNumber(seller.getPhoneNumber());
        return repository.save(seller1);
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
}

