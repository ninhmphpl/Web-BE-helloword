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
                                                          @PathVariable("amount") Long amount){
        Order oder = orderService.findById(idOrder).get();
        if(amount !=0) {oder.setAmount(amount);}else {oder.setAmount(1L);}

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
            order1.setTotal(order1.getAmount() * order1.getProductSimple().getPrice());
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

    @GetMapping("/info")
    public ResponseEntity<?> getInForBuyer() {
        return new ResponseEntity<>(buyerService.findById(1L).get(), HttpStatus.OK);
    }

    @PutMapping("cart/delete/{id}")
    public ResponseEntity<?> deleteOrderInCart(@PathVariable("id") Long id){
       Buyer buyer = buyerService.findById(1L).get();
       Order order = orderService.findById(id).get();
       List<Order> orderList = buyer.getCart();
       orderList.remove(order);
       buyer.setCart(orderList);
       buyerService.save(buyer);
       return new ResponseEntity<>(orderList,HttpStatus.OK);
    }
}

