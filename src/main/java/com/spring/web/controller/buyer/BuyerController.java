package com.spring.web.controller.buyer;

import com.spring.web.model.Buyer;
import com.spring.web.model.Order;
import com.spring.web.repository.BuyerRepository;
import com.spring.web.repository.OrderRepository;
import com.spring.web.service.impl.BuyerService;
import com.spring.web.service.impl.OrderService;
import com.spring.web.service.impl.ProductSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ProductSimpleService productSimpleService;

    @PostMapping("/to-cart/{id}/{quantity}")
    public ResponseEntity<?> findAllProductInCart(@PathVariable("id") Long id,
                                                  @PathVariable("quantity") Long quantity) {
        return new ResponseEntity<>( buyerService.findAllOrderInCart(id, quantity), HttpStatus.OK);
    }


}

