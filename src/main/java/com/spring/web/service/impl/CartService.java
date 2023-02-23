package com.spring.web.service.impl;

import com.spring.web.model.Bill;
import com.spring.web.model.Buyer;
import com.spring.web.model.Order;
import com.spring.web.model.pojo.Cart;
import com.spring.web.service.IOrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CartService {

    @Autowired
    private IOrderService orderService;
    public List<Cart> getCart(Buyer buyer) {
        List<Order> orders = buyer.getCart();
        List<Cart> carts = new ArrayList<>();
        while (orders.size() > 0){
            boolean flag = false;
            Order order = orders.get(0);
            orders.remove(0);
            for (Cart odr : carts){
                Long sellerId = order.getProductDetail().getSeller().getId();
                if(Objects.equals(sellerId, odr.getSeller().getId())){
                    odr.getOrders().add(order);
                    flag = true;
                    break;
                }
            }
            if(flag) continue;
            Cart cart = new Cart(order.getProductDetail().getSeller(), new ArrayList<>());
            cart.getOrders().add(order);
            carts.add(cart);
        }
        return carts;
    }

    public Bill buyCart(Cart cart){
        return null;
    }


}

