package com.spring.web.controller.buyer;

import com.spring.web.model.*;
import com.spring.web.repository.BuyerRepository;
import com.spring.web.repository.OrderRepository;
import com.spring.web.service.impl.BuyerService;
import com.spring.web.service.impl.OrderService;
import com.spring.web.service.impl.ProductDetailService;
import com.spring.web.service.impl.ProductSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Struct;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/buyer")
public class BuyerController {
    @Autowired
    public BuyerService buyerService;
    @Autowired
    public OrderService orderService;
    @Autowired
    public ProductDetailService productDetailService;


    @PostMapping("/to-cart/{id}/{quantity}")
    public ResponseEntity<?> findAllProductInCart(@PathVariable("id") Long id,
                                                  @PathVariable("quantity") Long quantity) {
        Optional<ProductDetail> productDetail = productDetailService.findById(id);
        if (productDetail.isPresent()) {
            if (productDetail.get().getQuantity() > quantity) {
                return new ResponseEntity<>(buyerService.findAllOrderInCart(id, quantity), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Số lượng vượt quá tồn kho,vui lòng sửa số lương", HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponseEntity<>("Không tìm thấy sản phẩm", HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/check-quantity")
    public ResponseEntity<ResultCheck> checkStockOrder(@RequestBody Order order) {
        return new ResponseEntity<>(buyerService.checkOrderQuantity(order), HttpStatus.OK);
    }


    @PutMapping("cart/buy")
    public ResponseEntity<?> makePayment(@RequestBody List<Order> orderList) {
        boolean flag = true;
        ResultCheck resultCheck = new ResultCheck();
        for (Order oder : orderList) {
            if (!checkStockOrder(oder).getBody().isStatus()) {
                flag = false;
                resultCheck = checkStockOrder(oder).getBody();
            }
        }
        if (flag) {
           Bill billPayment = buyerService.makeOnePayment(orderList) ;
            return new ResponseEntity<>(billPayment, HttpStatus.OK);
        } else{ return new ResponseEntity<>(resultCheck,HttpStatus.OK);}
    }
    @GetMapping("/info")
    public ResponseEntity<?>  getInForBuyer () {
        return new ResponseEntity<>(buyerService.findById(1L).get(),HttpStatus.OK);
    }

}

