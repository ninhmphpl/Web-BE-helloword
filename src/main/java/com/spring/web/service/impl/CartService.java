package com.spring.web.service.impl;

import com.spring.web.model.Buyer;
import com.spring.web.model.Order;
import com.spring.web.model.pojo.Cart;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CartService {
    public List<Cart> getCart(Buyer buyer) {
        List<Order> orders = buyer.getCart();
        List<Cart> carts = new ArrayList<>();
        while (orders.size() > 0){
            Order order = orders.get(0);
            for (Cart cart : carts){
                Long sellerId = order.getProductDetail().getSeller().getId();
                if(Objects.equals(sellerId, cart.getSeller().getId())){
                    cart.getOrders().add(order);
                    orders.remove(order);
                }
            }
            Cart cart = new Cart(order.getProductDetail().getSeller(), new ArrayList<>());
            cart.getOrders().add(order);
            carts.add(cart);
            orders.remove(order);
        }

        return carts;
    }


}

