package com.spring.web.controller.seller;

import com.spring.web.model.Seller;
import com.spring.web.repository.SellerRepository;
import com.spring.web.service.impl.SellerService;
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
    private SellerRepository sellerRepository;

    @GetMapping("/check-user-exist/{username}")
    public boolean checkUser(@PathVariable String username) {
        Seller seller = sellerService.findByUsername(username);
        if (seller!=null) {
            return true;
        } else {
            return false;
        }
    }
}
