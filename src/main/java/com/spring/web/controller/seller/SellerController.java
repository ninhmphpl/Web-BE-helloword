package com.spring.web.controller.seller;

import com.spring.web.model.Seller;
import com.spring.web.model.User;
import com.spring.web.service.impl.SellerService;
import com.spring.web.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/sigin")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private UserService userService;


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

    @PostMapping("/seller")
    public ResponseEntity<?> signUp(@RequestBody Seller seller , User user1 , Address address1) {
        User user = userService.saveInfoUser(user1);
        Address address = addressService.saveInfoAddress(address1);
        sellerService.saveInfoSeller( seller, user , address);
//        if (sellerService.signUp(seller)) {
//            return new ResponseEntity<>("Sign up successfully!", HttpStatus.CREATED);
//        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
