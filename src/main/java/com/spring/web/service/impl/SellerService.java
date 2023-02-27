package com.spring.web.service.impl;

import com.spring.web.model.*;
import com.spring.web.model.pojo.ArrayTool;
import com.spring.web.repository.AddressRepository;
import com.spring.web.repository.SellerRepository;
import com.spring.web.repository.UserRepository;
import com.spring.web.service.IProductDetailService;
import com.spring.web.service.ISellerService;
import com.spring.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional // Khi lỗi thì không được lưu gì vào DataBase
public class SellerService implements ISellerService {
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserService userService;
    @Autowired
    private IProductDetailService productDetailService;

    @Override

    public Optional<Seller> findById(Long aLong) {
        return sellerRepository.findById(aLong);
    }

    @Override
    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }

    @Override
    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    public void delete(Long aLong) {
        sellerRepository.deleteById(aLong);
    }

    @Override
    public Object create(Seller seller, String username, String password) {
        Iterable<Seller> sellers = sellerRepository.findAll();
        for (Seller seller1 : sellers) {
            if (seller1.getUser().getUsername().equals(username)) {
                return "303, User Đã Bị Trùng";
            }
        }
        seller.setId(null);
        User user1 = new User();
        user1.setRole(new Role(3L, null));
        user1.setStatus(new Status(1L, null, null));
        user1.setId(null);
        user1.setUsername(username);
        user1.setPassword(passwordEncoder.encode(password));
        user1 = userRepository.save(user1);
        Address address1 = addressRepository.save(seller.getAddress());
        seller.setAddress(address1);
        seller.setUser(user1);
        seller.setDescription(" Không có thông tin");
        return sellerRepository.save(seller);
    }

    @Override
    public Optional<Seller> getSeller() {
        User user = userService.getUserLogging();
        return sellerRepository.findByUser(user);
    }

    @Override
    public boolean checkProductIdOfSeller(Long productId, Seller seller) {
        for(ProductDetail productDetail : seller.getListProduct()){
            if(Objects.equals(productDetail.getId(), productId)) return true;
        }
        return false;
    }

    @Override
    public ProductDetail changeStatusProduct(Seller seller, Long productId, Long statusId) {
        List<ProductDetail> productDetails = seller.getListProduct();
        for(ProductDetail productDetail : productDetails){
            if(Objects.equals(productDetail.getId(), productId)){
                productDetail.setStatus(new Status(statusId));
                return productDetailService.save(productDetail);
            }
        }
        return null;
    }

    @Override
    public List<Bill> getAllBillSortDesc(Seller seller) {
        for(Bill bill : seller.getBill()){
            bill.setBill();
        }
        return new ArrayTool<Bill>().reverse(seller.getBill());
    }

    public List<Bill> getAllByStatus(Seller seller, long statusId){
        List<Bill> bills = getAllBillSortDesc(seller);
        for(int i = 0 ; i < bills.size() ; i ++){
            if(bills.get(i).getStatus().getId() != statusId){
                bills.remove(i);
                i--;
            }
        }
        return bills;
    }

    @Override
    public Page<Seller> findAllSellerPage(Pageable pageable) {
        return sellerRepository.findAll(pageable);
    }

    @Override
    public Seller findByProductDetailContaining(ProductDetail productDetail) {
        return sellerRepository.findByListProductContaining(productDetail);
    }


    @Override
    public List<Seller> finaAllSellerByNameContaining(String name) {
        return sellerRepository.findAllByNameContaining(name);
    }

}

