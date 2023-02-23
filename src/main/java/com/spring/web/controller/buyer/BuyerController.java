package com.spring.web.controller.buyer;

import com.spring.web.model.*;
import com.spring.web.model.pojo.Cart;
import com.spring.web.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
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
    @Autowired
    CartService cartService;


    @PostMapping("/to-cart/{id}/{quantity}")
    public ResponseEntity<?> findAllProductInCart(@PathVariable("id") Long id,
                                                  @PathVariable("quantity") Long quantity) {
        Optional<ProductDetail> productDetail = productDetailService.findById(id);

        if (productDetail.isPresent()) {
            if (productDetail.get().getQuantity() > quantity) {
                return new ResponseEntity<>(buyerService.findAllOrderInCart(id, quantity), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("400, Số lượng vượt quá tồn kho vui lòng sửa số lương", HttpStatus.OK);
            }

        } else {
            return new ResponseEntity<>("Không tìm thấy sản phẩm", HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/check-quantity")
    public ResponseEntity<?> checkStockOrder(@RequestBody Order order) {
        Boolean result = buyerService.checkOrderQuantity(order).isStatus();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("cart/edit-amount/{idOrder}/{amount}")
    public ResponseEntity<?> checkStockOrderAfterEditCart(@PathVariable("idOrder") Long idOrder,
                                                          @PathVariable("amount") Long amount) {
        Order oder = orderService.findById(idOrder).get();
        if (amount != 0) {
            oder.setAmount(amount);
        } else {
            oder.setAmount(1L);
        }

        Boolean result = buyerService.checkOrderQuantity(oder).isStatus();
        if (result) {
            orderService.save(oder);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PutMapping("cart/buy")
    public ResponseEntity<?> makePayment(@RequestBody List<ResultFE> orderList) {
        List<Order> orderArrayList = new ArrayList<>();
        for (ResultFE oder : orderList
        ) {
            Order order1 = orderService.findById(oder.getId()).get();
            order1.setAmount(oder.getAmount());
            order1.setTotal(order1.getAmount() * order1.getProductDetail().getPrice());
            orderArrayList.add(order1);
        }
        boolean flag = true;
        for (Order oder : orderArrayList) {
            if (!buyerService.checkOrderQuantity(oder).isStatus()) {
                flag = false;
            }
        }
        if (flag) {
            Bill billPayment = buyerService.makeOnePayment(orderArrayList);
            return new ResponseEntity<>(billPayment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Số lượng sản phẩm đặt hàng vượt quá tồn kho", HttpStatus.OK);
        }
    }

    public Object buyCart(Cart cart){
        return null;
    }

    @GetMapping("/info")
    public Object getInForBuyer() {
        Optional<Buyer> buyer = buyerService.getBuyer();
        if (!buyer.isPresent()) return "404, Người mua không tồn tại";
        return new ResponseEntity<>(buyer.get(), HttpStatus.OK);
    }

    // xoa cart khoi nguoi dung
    @PutMapping("cart/delete/{id}")
    public ResponseEntity<?> deleteOrderInCart(@PathVariable("id") Long id) {
        Optional<Buyer> buyer = buyerService.getBuyer();
        if(buyer.isPresent()){
            Object delete = orderService.deleteOderOfBuyer(buyer.get(), id);
            if(delete == null){
                return new ResponseEntity<>(HttpStatus.OK);
            }return new ResponseEntity<>(delete, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Buyer not found", HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/cart")
    public Object getCart() {
        Optional<Buyer> buyer = buyerService.getBuyer();
        if (buyer.isPresent()) {
            return new ResponseEntity<>(cartService.getCart(buyer.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>("Người mua không tồn tại", HttpStatus.BAD_REQUEST);
    }
}

