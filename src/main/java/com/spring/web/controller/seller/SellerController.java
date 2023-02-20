package com.spring.web.controller.seller;

import com.spring.web.model.ProductDetail;
import com.spring.web.model.ProductSimple;
import com.spring.web.model.Seller;
import com.spring.web.model.User;
import com.spring.web.repository.SellerRepository;
import com.spring.web.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@CrossOrigin("*")
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductSimpleService productSimpleService;


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
    public ResponseEntity<?> signUp(@RequestBody Seller seller) {
        Object seller1 = sellerService.create(seller);
        return new ResponseEntity<>(seller1,HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAllSellerPage(@PageableDefault(value = 10)
                                               @SortDefault(sort = "id", direction = ASC) Pageable pageable) {
        Page<Seller> page = sellerService.findAllSellerPage(pageable);
        if (pageable.getPageNumber() >= page.getTotalPages() || pageable.getPageNumber() < 0) {
            System.out.println("Page Number out range page");
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
    @PostMapping("/create-product/{id}")
    public ResponseEntity<?> create(@RequestBody ProductDetail productDetail,
                                    @PathVariable Long id){
        Seller seller1= sellerService.findById(id).get();
        ProductDetail productDetail1 = productDetailService.createProduct(productDetail);
        ProductSimple productSimple1 = productSimpleService.findById(productDetail1.getId()).get();
        List<ProductSimple> productSimpleList = seller1.getListProduct();
        productSimpleList.add(productSimple1);
        seller1.setListProduct(productSimpleList);
        sellerService.save(seller1);
        return new ResponseEntity<>(productDetail1, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findAllSellerByNameContaining(@PathVariable String name){
        List<Seller> sellerList = sellerService.finaAllSellerByNameContaining(name);
        for (Seller seller : sellerList){
            seller.setAddress(null);
            seller.setListProduct(null);
            seller.setUser(null);
        }
        return new ResponseEntity<>(sellerList, HttpStatus.OK);
    }

}
