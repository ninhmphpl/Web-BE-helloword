package com.spring.web.service.impl;

import com.spring.web.model.Bill;
import com.spring.web.repository.BillRepository;
import com.spring.web.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService implements IBillService {
    @Autowired
    private BillRepository billRepository;
    @Override
    public Optional<Bill> findById(Long aLong) {
        return billRepository.findById(aLong);
    }

    @Override
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public void delete(Long aLong) {
        billRepository.deleteById(aLong);

    }
}
