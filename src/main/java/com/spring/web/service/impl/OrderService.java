package com.spring.web.service.impl;

import com.spring.web.model.Order;
import com.spring.web.repository.OrderRepository;
import com.spring.web.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrderService implements IOrderService {

    @Autowired
    public OrderRepository repository;

    @Override
    public Optional<Order> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);

    }
}