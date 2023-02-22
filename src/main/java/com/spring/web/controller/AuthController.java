package com.spring.web.controller;

import com.spring.web.model.Buyer;
import com.spring.web.model.Seller;
import com.spring.web.model.User;
import com.spring.web.security.jwt.JwtResponse;
import com.spring.web.security.jwt.JwtService;
import com.spring.web.service.IBuyerService;
import com.spring.web.service.ISellerService;
import com.spring.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ISellerService sellerService;

    @Autowired
    private IBuyerService buyerService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), currentUser.getUsername(), userDetails.getAuthorities()));
    }
    // dang ky seller Mới
    @PostMapping("signIn/seller/{username}/{password}")
    public ResponseEntity<?> signUpSeller(@RequestBody Seller seller, @PathVariable String username, @PathVariable String password) {
        Object seller1 = sellerService.create(seller, username, password);
        return new ResponseEntity<>(seller1,HttpStatus.OK);
    }

    @PostMapping("signIn/buyer/{username}/{password}")
    public ResponseEntity<?> signUpBuyer(@RequestBody Buyer buyer, @PathVariable String username, @PathVariable String password) {
        Object result = buyerService.createBuyer(buyer, username, password);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }
}
