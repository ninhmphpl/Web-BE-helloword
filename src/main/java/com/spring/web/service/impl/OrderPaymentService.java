package com.spring.web.service.impl;

import com.spring.web.model.OrderPayment;
import com.spring.web.repository.OrderPaymentRepository;
import com.spring.web.service.IOrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrderPaymentService implements IOrderPaymentService {
    @Autowired
    private OrderPaymentRepository orderPaymentRepository;
    @Override
    public Optional<OrderPayment> findById(Long aLong) {
        return  orderPaymentRepository.findById(aLong);
    }

    @Override
    public List<OrderPayment> findAll() {
        return orderPaymentRepository.findAll();
    }

    @Override
    public OrderPayment save(OrderPayment orderPayment) {
        return orderPaymentRepository.save(orderPayment);
    }

    @Override
    public void delete(Long aLong) {
orderPaymentRepository.deleteById(aLong);
    }
}
