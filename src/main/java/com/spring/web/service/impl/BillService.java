package com.spring.web.service.impl;

import com.spring.web.model.*;
import com.spring.web.repository.BillRepository;
import com.spring.web.repository.OrderPaymentRepository;
import com.spring.web.service.IBillService;
import com.spring.web.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BillService implements IBillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private OrderPaymentRepository orderPaymentRepository;

    @Autowired
    private INotificationService notificationService;
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

    @Override
    public Bill setStatusBillSeller(Seller seller, Long billId, Long statusId, String note) {
        Bill bill = findIdBillOnList(seller.getBill(), billId);
        if(bill != null){
            bill.setStatus(new Status(statusId));
            bill.setNote(note);
            Bill billResult =  billRepository.save(bill);
            Notification notificationBuyer = new Notification(bill.getBuyer(), billResult);
            Notification notificationSeller = new Notification(bill.getSeller(), billResult);
            notificationService.save(notificationSeller);
            notificationService.save(notificationBuyer);
            return billResult;
        }
        return null;
    }

    private Bill findIdBillOnList(List<Bill> list, Long id){
        for (Bill bill : list){
            if (Objects.equals(bill.getId(), id)) return bill;
        }
        return null;
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
