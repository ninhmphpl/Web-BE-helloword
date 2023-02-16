package com.spring.web.controller.seller;

import com.spring.web.model.Role;
import com.spring.web.model.Seller;
import com.spring.web.model.User;
import com.spring.web.repository.RoleRepository;
import com.spring.web.repository.SellerRepository;
import com.spring.web.service.impl.RoleService;
import com.spring.web.service.impl.SellerService;
import com.spring.web.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/sigin")
public class SellerController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

// check User tồn tại hay chưa
    @GetMapping("/check-user-exist/{username}")
    public boolean checkUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user!=null) {
            return true;
        } else {
            return false;
        }
    }

    //Find Seller theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Seller> findById(@PathVariable Long id){
        return new ResponseEntity<>(sellerService.findById(id).get(),HttpStatus.OK);
    }

    @PostMapping("/seller")
    public ResponseEntity<String> signUp(@RequestBody User users) {
        if (userService.signUp(users)) {
            return new ResponseEntity<>("Sign up successfully!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    }


