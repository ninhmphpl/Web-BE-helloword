package com.spring.web.service;

import com.spring.web.model.Bill;
import com.spring.web.model.Buyer;
import com.spring.web.model.Seller;

import java.util.List;
import java.util.Optional;

public interface IBillService extends CRUDService<Bill,Long>{
    Bill checkBillOfBuyer(Buyer buyer, Long id);
    List<Bill> getAllBill(Buyer buyer);
    List<Bill> getAllBillDone(Buyer buyer);
    public List<Bill> getAllBillCancel(Buyer buyer);
    Bill setStatusBillSeller(Seller seller, Long billId, Long statusId, String note);

}
