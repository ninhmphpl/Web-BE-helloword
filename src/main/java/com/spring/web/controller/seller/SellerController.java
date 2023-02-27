package com.spring.web.controller.seller;

import com.spring.web.model.*;
import com.spring.web.service.IBillService;
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
    @Autowired
    private IBillService billService;


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
    @GetMapping("/info")
    public ResponseEntity<?> getInfoSeller(){
        Optional<Seller> seller = sellerService.getSeller();
        if(seller.isPresent()){
            return new ResponseEntity<>(seller.get(), HttpStatus.OK);
        }else return new ResponseEntity<>("Người bán này không tồn tại", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/notification")
    public ResponseEntity<?> getNotification(){
        Optional<Seller> seller = sellerService.getSeller();
        if(seller.isPresent()){
            List<Notification> notifications = seller.get().setValueNotification();
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        }else return new ResponseEntity<>("Người bán này không tồn tại", HttpStatus.BAD_REQUEST);
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
    @PutMapping("/editPicture/{id}")
    public ResponseEntity<?> editPictureProduct(@RequestBody List<Picture> pictures,@PathVariable Long id){
        Optional<Seller> seller = sellerService.getSeller();
        if (seller.isPresent()) {
            if (!sellerService.checkProductIdOfSeller(id, seller.get())) return new ResponseEntity<>("Người mua không có quyền thay đổi sản phẩm", HttpStatus.BAD_REQUEST);
            ProductDetail productResult = productDetailService.updateImage(id, pictures);
            return new ResponseEntity<>(productResult, HttpStatus.OK);
        } else return new ResponseEntity<>("Người bán không tồn tại", HttpStatus.BAD_REQUEST);
    }
    //>> Lấy danh sách đơn hàng của người bán
    @GetMapping("/bill")
    public ResponseEntity<?> getAllBill (){
        Optional<Seller> seller = sellerService.getSeller();
        if (seller.isPresent()) {
            List<Bill> bills = sellerService.getAllBillSortDesc(seller.get());
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } else return new ResponseEntity<>("Người bán không tồn tại", HttpStatus.BAD_REQUEST);

    }
    @GetMapping("/bill/wait")
    public ResponseEntity<?> getAllBillWait (){
        Optional<Seller> seller = sellerService.getSeller();
        if (seller.isPresent()) {
            List<Bill> bills = sellerService.getAllByStatus(seller.get(), 5L);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } else return new ResponseEntity<>("Người bán không tồn tại", HttpStatus.BAD_REQUEST);

    }
    @GetMapping("/bill/done")
    public ResponseEntity<?> getAllBillDone (){
        Optional<Seller> seller = sellerService.getSeller();
        if (seller.isPresent()) {
            List<Bill> bills = sellerService.getAllByStatus(seller.get(), 6L);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } else return new ResponseEntity<>("Người bán không tồn tại", HttpStatus.BAD_REQUEST);

    }
    @GetMapping("/bill/cancel")
    public ResponseEntity<?> getAllBillCancel (){
        Optional<Seller> seller = sellerService.getSeller();
        if (seller.isPresent()) {
            List<Bill> bills = sellerService.getAllByStatus(seller.get(), 7L);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } else return new ResponseEntity<>("Người bán không tồn tại", HttpStatus.BAD_REQUEST);

    }

    //>> Đồng ý đơn hàng
    @GetMapping("/bill/done/{id}")
    public ResponseEntity<?> doneBill (@PathVariable Long id){
        Optional<Seller> seller = sellerService.getSeller();
        if (seller.isPresent()) {
            Bill bill = billService.setStatusBillSeller(seller.get(), id, 6L, null);
            return new ResponseEntity<>(bill, HttpStatus.OK);
        } else return new ResponseEntity<>("Người bán không tồn tại", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/bill/cancel/{id}/{note}")
    public ResponseEntity<?> cancelBill (@PathVariable Long id, @PathVariable String note){
        Optional<Seller> seller = sellerService.getSeller();
        if (seller.isPresent()) {
            Bill bill = billService.setStatusBillSeller(seller.get(), id, 7L, note);
            return new ResponseEntity<>(bill, HttpStatus.OK);
        } else return new ResponseEntity<>("Người bán không tồn tại", HttpStatus.BAD_REQUEST);
    }




}
