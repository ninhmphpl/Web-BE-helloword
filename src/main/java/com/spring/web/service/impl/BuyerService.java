package com.spring.web.service.impl;

import com.spring.web.model.Buyer;
import com.spring.web.model.Order;
import com.spring.web.repository.BuyerRepository;
import com.spring.web.service.IBuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class BuyerService implements IBuyerService {
    @Autowired
    private BuyerRepository repository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductSimpleService productSimpleService;

    @Override
    public Optional<Buyer> findById(Long aLong) {

        return repository.findById(aLong);
    }

    @Override
    public List<Buyer> findAll() {

        return repository.findAll();
    }

    @Override
    public Buyer save(Buyer buyer) {

        return repository.save(buyer);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);

    }

    public Object findAllOrderInCart(Long id, Long quantity) {
        Long buyerIid = 11L;
        Optional<Buyer> buyer = repository.findById(buyerIid);
        if (buyer.isPresent()) {
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
                        flag = false;
                    }
                }
                if (flag) {

                    Order newOrder = new Order(null, productSimpleService.findById(id).get(), quantity, null);
                    Order orderCreated = orderService.save(newOrder);
                    orderLists.add(orderCreated);
                    buyer.get().setCart(orderLists);
                    repository.save(buyer.get());
                }
            } else {
                Order newOrder = new Order(null, productSimpleService.findById(id).get(), quantity, null);
                Order oderCreate = orderService.save(newOrder);
                buyer.get().getCart().add(oderCreate);
                repository.save(buyer.get());
            }

            return repository.findById(buyerIid).get().getCart();
        } else {
            return "403,không có người dùng";
        }
    }


}
