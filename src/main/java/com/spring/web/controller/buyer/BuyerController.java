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

        Optional<Buyer> buyer = buyerService.findById(4L);
        List<Order> orderLists = buyer.get().getCart();
        Long orderId = 0L;
        if (orderLists.size() != 0) {
            boolean flag = true;
            for (Order cart : orderLists) {
                if (cart.getProductSimple().getId().equals(id)) {
                    orderId = cart.getId();
                    Order order = orderService.findById(orderId).get();
                    Long amount = order.getAmount();
                    order.setAmount(amount + quantity);
                    orderService.save(order);
                    flag=false;
                    return new ResponseEntity<>(buyerService.findById(4L).get().getCart(), HttpStatus.OK);
                }
            }
            if(flag){

                    Order newOrder = new Order(null, productSimpleService.findById(id).get(), quantity, null);
                    Order orderCreated = orderService.save(newOrder);
                    orderLists.add(orderCreated);
                    buyer.get().setCart(orderLists);
                    buyerService.save(buyer.get());
                    orderLists = buyerService.findById(4L).get().getCart();
                    return new ResponseEntity<>(orderLists, HttpStatus.OK);
            }

        } else {
            Order newOrder = new Order(null, productSimpleService.findById(id).get(), quantity, null);
         Order oderCreate =  orderService.save(newOrder);
         buyer.get().getCart().add(oderCreate);
         buyerService.save(buyer.get());
            return new ResponseEntity<>(buyerService.findById(4L).get().getCart(), HttpStatus.OK);
        }
        return new ResponseEntity<>(buyerService.findById(4L).get().getCart(), HttpStatus.OK);
    }
}

