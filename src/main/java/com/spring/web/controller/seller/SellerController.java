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


    @GetMapping("/check-user-exist/{username}")
    public boolean checkUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user!=null) {
            return true;
        } else {
            return false;
        }
    }
    @PostMapping("/seller")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Iterable<User> users = userService.findAll();
        for (User currentUser : users) {
            if (currentUser.getId().equals(user.getId())) {
                return new ResponseEntity<>("Tên người dùng đã tồn tại", HttpStatus.BAD_REQUEST);
            }
        }
        user.setPassword(user.getPassword());
        userService.save(user);
        return new ResponseEntity<>("lỗi", HttpStatus.CREATED);
    }
}
