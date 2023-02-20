package com.spring.web.service.impl;

import com.spring.web.model.*;
import com.spring.web.repository.BillRepository;
import com.spring.web.repository.BuyerRepository;
import com.spring.web.repository.OrderPaymentRepository;
import com.spring.web.repository.ProductDetailRepository;
import com.spring.web.service.IBuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private BillService billService;

    @Autowired
    private OrderPaymentRepository orderPaymentRepository;

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

        Optional<Buyer> buyer = repository.findById(1L);
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

            return repository.findById(1L).get().getCart();
        } else {
            return "403,không có người dùng";
        }
    }

    public ResultCheck checkOrderQuantity(Order order) {
        Optional<Buyer> buyer = repository.findById(1L);
        if (buyer.isPresent()) {
            Long idProduct = order.getProductSimple().getId();
            Integer stock = productDetailRepository.findById(idProduct).get().getQuantity();
            String nameProduct = productDetailRepository.findById(idProduct).get().getName();
            if (stock >= order.getAmount()) {
                return new ResultCheck(true, null, null);
            } else {
                return new ResultCheck(false, "Tồn kho sản phẩm: " + nameProduct + " chỉ còn :", stock);
            }
        } else {
            return new ResultCheck(false, "Người mua hàng đã bị xóa hoặc không tồn tại", null);
        }
    }


    //tạo hóa đơn và thay đổi giỏ hàng
    public Bill makeOnePayment(List<Order> orders) {
//        orderPayments list order
        List<OrderPayment> orderPayments = new ArrayList<>(orders.size());
        for (int i = 0; i < orders.size(); i++) {
            OrderPayment  orderPayment = new OrderPayment(orders.get(i).getId(), orders.get(i).getProductSimple(), orders.get(i).getAmount(), orders.get(i).getTotal());
               orderPaymentRepository.save(orderPayment);
                orderPayments.add(orderPayment);

        }

        Buyer buyer = repository.findById(1L).get();
        List<Bill> billList = buyer.getBills();
        Bill newBill = new Bill(null, orderPayments, LocalDateTime.now(), null, buyer.getId(), buyer.getName());
        newBill.setTotal(newBill.totalPayment());
        newBill = billService.save(newBill);
        billList.add(newBill);
        buyer.setBills(billList);



        for (Order order : orders){
            buyer.getCart().remove(order);
        }
        repository.save(buyer);

//  oders là list khách gửi về
        for (Order order : orders) {
            setProductDetail(order);
            deleteCartAfterPay(order);
        }
        return newBill;
    }

    //    thay đổi quantity và sold của productdetail
    public void setProductDetail(Order order) {
        Long idProduct = order.getProductSimple().getId();
        ProductDetail productDetail = productDetailRepository.findById(idProduct).get();
        Integer soldOld = productDetail.getSold();
        Integer soldNew = order.getAmount().intValue();
        Integer stock = productDetail.getQuantity();
        productDetail.setSold(soldNew + soldOld);
        productDetail.setQuantity(stock - soldNew);
        productDetailRepository.save(productDetail);
    }

    /// sửa lại giỏ hàng sau khi thanh toán
    public void deleteCartAfterPay(Order order) {
   orderService.delete(order.getId());
    }
}
