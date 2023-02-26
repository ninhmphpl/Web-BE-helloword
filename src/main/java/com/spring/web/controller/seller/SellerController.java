package com.spring.web.controller.seller;

import com.spring.web.model.ProductDetail;
import com.spring.web.model.Seller;
import com.spring.web.model.Status;
import com.spring.web.model.User;
import com.spring.web.service.ISellerService;
import com.spring.web.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@CrossOrigin("*")
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    private ISellerService sellerService;
    @Autowired
    private UserService userService;
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
                                @PathVariable Long id) {
        Optional<Seller> seller = sellerService.findById(id);
        if (!seller.isPresent()) return "404, Người bán này không còn tồn tại trong hệ thống";
        return productDetailService.createProductByEmployee(productDetail, id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findAllSellerByNameContaining(@PathVariable String name) {
        List<Seller> sellerList = sellerService.finaAllSellerByNameContaining(name);
        for (Seller seller : sellerList) {
            seller.setAddress(null);
            seller.setListProduct(null);
            seller.setUser(null);
        }
        return new ResponseEntity<>(sellerList, HttpStatus.OK);
    }

    //>> Lấy danh sách sản phẩm của người bán
    @GetMapping("/product")
    public ResponseEntity<?> findAllProductBySellerPage(@PageableDefault(size = 10)
                                                        @SortDefault(sort = "id", direction = DESC)
                                                        Pageable pageable) {
        Optional<Seller> seller = sellerService.getSeller();
        if (seller.isPresent()) {
            Page<ProductDetail> productDetailPage = productDetailService.findAllBySeller(seller.get(), pageable);
            return new ResponseEntity<>(productDetailPage, HttpStatus.OK);
        } else return new ResponseEntity<>("Người bán không còn tồn tại", HttpStatus.BAD_REQUEST);
    }

    //>> ngừng bán sản phẩm
    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> stopSellProduct(@PathVariable("id") Long id) {
        Optional<Seller> seller = sellerService.getSeller();
        if (seller.isPresent()) {
            ProductDetail productDetail = sellerService.changeStatusProduct(seller.get(), id, 4L);
            if (productDetail != null) {
                return new ResponseEntity<>(productDetail, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Sản phẩm không tại trong danh sách người người bán", HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>("Người mua không tồn tại", HttpStatus.BAD_REQUEST);
    }

    //>> mở bán sản phẩm
    @GetMapping("/product/open/{id}")
    public ResponseEntity<?> openSellProduct(@PathVariable("id") Long id) {
        Optional<Seller> seller = sellerService.getSeller();
        if (seller.isPresent()) {
            ProductDetail productDetail = sellerService.changeStatusProduct(seller.get(), id, 3L);
            if (productDetail != null) {
                return new ResponseEntity<>(productDetail, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Sản phẩm không tại trong danh sách người người bán", HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>("Người bán không tồn tại", HttpStatus.BAD_REQUEST);
    }

    //>> tạo sản phẩm cho người bán
    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@RequestBody ProductDetail productDetail) {
        Optional<Seller> seller = sellerService.getSeller();
        if (seller.isPresent()) {
            ProductDetail productResult = productDetailService.createProductForSeller(productDetail, seller.get());
            return new ResponseEntity<>(productResult, HttpStatus.OK);
        } else return new ResponseEntity<>("Người bán không tồn tại", HttpStatus.BAD_REQUEST);
    }
    //>> update sản phẩm cho người bán
    @PutMapping("/product")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDetail productDetail){
        Optional<Seller> seller = sellerService.getSeller();
        if (seller.isPresent()) {
            ProductDetail productResult = productDetailService.updateProductForSeller(productDetail, seller.get());
            return new ResponseEntity<>(productResult, HttpStatus.OK);
        } else return new ResponseEntity<>("Người bán không tồn tại", HttpStatus.BAD_REQUEST);
    }


}
