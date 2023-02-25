package com.spring.web.service.impl;

import com.spring.web.model.Bill;
import com.spring.web.model.Buyer;
import com.spring.web.model.OrderPayment;
import com.spring.web.repository.BillRepository;
import com.spring.web.repository.OrderPaymentRepository;
import com.spring.web.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BillService implements IBillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private OrderPaymentRepository orderPaymentRepository;
    @Override
    public Optional<Bill> findById(Long id) {
        return billRepository.findById(id);
    }

    @Override
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public Bill save(Bill bill) {
        Bill billResult = billRepository.save(bill);
        for (OrderPayment orderPayment : bill.getOrderPayments()){
            orderPayment.setBill(billResult);
            orderPaymentRepository.save(orderPayment);
        }
        return billResult;
    }

    @Override
    public void delete(Long aLong) {
        billRepository.deleteById(aLong);

    }

    @Override
    public Bill checkBillOfBuyer(Buyer buyer, Long id) {
        List<Bill> bills = buyer.getBills();
        for(Bill bill : bills){
            if(Objects.equals(bill.getId(), id)){
                return bill.setBill();
            }
        }
        return null;
    }
    public List<Bill> getAllBill(Buyer buyer){
        List<Bill> bills =  buyer.getBills();
        for (Bill bill : bills){
            bill.setBill();
        }
        List<Bill> billSortDESC = new ArrayList<>();
        for (int i = bills.size() - 1; i >= 0  ; i--) {
            billSortDESC.add(bills.get(i));
        }
        return billSortDESC;
    }

    public List<Bill> getAllBillDone(Buyer buyer){
        return getAllByStatus(buyer, 6L);
    }

    public List<Bill> getAllBillCancel(Buyer buyer){
        return getAllByStatus(buyer, 7L);
    }

    public List<Bill> getAllByStatus(Buyer buyer, long statusId){
        List<Bill> bills = getAllBill(buyer);
        for(int i = 0 ; i < bills.size() ; i ++){
            if(bills.get(i).getStatus().getId() != statusId){
                bills.remove(i);
                i--;
            }
        }
        return bills;
    }
}
