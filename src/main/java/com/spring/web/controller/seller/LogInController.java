package com.spring.web.controller.seller;

import com.spring.web.model.*;
import com.spring.web.repository.SellerRepository;
import com.spring.web.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/sigin")
public class LogInController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ProductDetailService productDetailService;

    // check User tồn tại hay chưa
    @GetMapping("/check-user-exist/{username}")
    public boolean checkUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    //Find Seller theo ID
    @GetMapping("/seller/{id}")
    public ResponseEntity<Seller> findById(@PathVariable Long id) {
        return new ResponseEntity<>(sellerService.findById(id).get(), HttpStatus.OK);
    }
    // Tạo Nick Mới
    @PostMapping("/seller")
    public ResponseEntity<?> signUp(@RequestBody Seller seller) {
        Object seller1 = sellerService.create(seller);
        return new ResponseEntity<>(seller1,HttpStatus.OK);
    }
}
