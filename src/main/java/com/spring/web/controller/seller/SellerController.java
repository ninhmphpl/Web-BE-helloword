package com.spring.web.controller.seller;

import com.spring.web.model.ProductDetail;
import com.spring.web.model.Seller;
import com.spring.web.model.User;
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
import java.util.Optional;

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


//    // check User tồn tại hay chưa
//    @GetMapping("/check-user-exist/{username}")
//    public boolean checkUser(@PathVariable String username) {
//        User user = userService.findByUsername(username);
//        if (user != null) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    //Find Seller theo ID
    @GetMapping("/seller/{id}")
    public ResponseEntity<Seller> findById(@PathVariable Long id) {
        return new ResponseEntity<>(sellerService.findById(id).get(), HttpStatus.OK);
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
    public Object createProduct(@RequestBody ProductDetail productDetail,
                                    @PathVariable Long id){
        Optional<Seller> seller = sellerService.findById(id);
        if(!seller.isPresent()) return "404, Người bán này không còn tồn tại trong hệ thống";
        return productDetailService.createProductByEmployee(productDetail, id);
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
