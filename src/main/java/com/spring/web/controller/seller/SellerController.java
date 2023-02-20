package com.spring.web.controller.seller;

import com.spring.web.model.Employee;
import com.spring.web.model.ProductDetail;
import com.spring.web.model.Seller;
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
    private SellerRepository sellerRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ProductDetailService productDetailService;
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
    // Tạo Sản Phảm mới từ Sellet
    @PostMapping("/create-product")
    public ResponseEntity<ProductDetail> create(@RequestBody ProductDetail productDetail){
        ProductDetail productResult = productDetailService.createProduct(productDetail);
        return new ResponseEntity<>(productResult, HttpStatus.OK);
    }
}
