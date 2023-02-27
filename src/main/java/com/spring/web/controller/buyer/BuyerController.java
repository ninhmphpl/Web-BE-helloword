package com.spring.web.controller.buyer;

import com.spring.web.model.*;
import com.spring.web.model.pojo.Cart;
import com.spring.web.service.IBillService;
import com.spring.web.service.INotificationService;
import com.spring.web.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/buyer")
public class BuyerController {
    @Autowired
    public BuyerService buyerService;
    @Autowired
    public OrderService orderService;
    @Autowired
    public ProductDetailService productDetailService;
    @Autowired
    private CartService cartService;

    @Autowired
    private INotificationService notificationService;
    @Autowired
    private IBillService billService;



    @PostMapping("/to-cart/{id}/{quantity}")
    public ResponseEntity<?> findAllProductInCart(@PathVariable("id") Long id,
                                                  @PathVariable("quantity") Long quantity) {
        Optional<ProductDetail> productDetail = productDetailService.findById(id);

        if (productDetail.isPresent()) {
            if (productDetail.get().getQuantity() > quantity) {
                return new ResponseEntity<>(buyerService.findAllOrderInCart(id, quantity), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("400,Quantity Exceeds Stock ", HttpStatus.OK);
            }

        } else {
            return new ResponseEntity<>("Product is not found", HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/check-quantity")
    public ResponseEntity<?> checkStockOrder(@RequestBody Order order) {
        Boolean result = buyerService.checkOrderQuantity(order).isStatus();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("cart/edit-amount/{idOrder}/{amount}")
    public ResponseEntity<?> plusOrder(@PathVariable("idOrder") Long idOrder,
                                       @PathVariable("amount") Long amount) {

        Optional<Buyer> buyer = buyerService.getBuyer();
        Optional<Order> order = orderService.findById(idOrder);
        if (!buyer.isPresent()) return new ResponseEntity<>("Buyer Not Found", HttpStatus.BAD_REQUEST);
        if (!order.isPresent()) return new ResponseEntity<>("Order Not Found", HttpStatus.BAD_REQUEST);
        if (order.get().getBuyer().getId() == buyer.get().getId()) {
            Order result = orderService.addOder(order.get(), buyer.get(), amount);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>("Order is not of Buyer", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/cart/set-amount/{orderId}/{amount}")
    public ResponseEntity<?> setAmount(@PathVariable("orderId") Long idOrder,
                                       @PathVariable("amount") Long amount) {
        Optional<Order> oder = orderService.findById(idOrder);
        boolean result;
        if (oder.isPresent()) {
            if (amount > 0) {
                oder.get().setAmount(amount);
            } else {
                oder.get().setAmount(1L);
            }
            result = buyerService.checkOrderQuantity(oder.get()).isStatus();
            if (result) {
                orderService.save(oder.get());
            }
        } else {
            return new ResponseEntity<>("Order không tồn tại", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);

    }


    @PutMapping("cart/buy")
    public ResponseEntity<?> makePayment(@RequestBody List<ResultFE> orderList) {
        List<Order> orderArrayList = new ArrayList<>();
        for (ResultFE oder : orderList
        ) {
            Order order1 = orderService.findById(oder.getId()).get();
            order1.setAmount(oder.getAmount());
            order1.setTotal(order1.getAmount() * order1.getProductDetail().getPrice());
            orderArrayList.add(order1);
        }
        boolean flag = true;
        for (Order oder : orderArrayList) {
            if (!buyerService.checkOrderQuantity(oder).isStatus()) {
                flag = false;
            }
        }
        if (flag) {
            Bill billPayment = buyerService.makeOnePayment(orderArrayList);
            return new ResponseEntity<>(billPayment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Số lượng sản phẩm đặt hàng vượt quá tồn kho", HttpStatus.OK);
        }
    }

    public Object buyCart(Cart cart) {
        return null;
    }

    @GetMapping("/info")
    public Object getInForBuyer() {
        Optional<Buyer> buyer = buyerService.getBuyer();
        if (!buyer.isPresent()) return "404, Người mua không tồn tại";
        return new ResponseEntity<>(buyer.get(), HttpStatus.OK);
    }

    // xoa cart khoi nguoi dung
    @DeleteMapping("cart/delete/{id}")
    public ResponseEntity<?> deleteOrderInCart(@PathVariable("id") Long id) {
        Optional<Buyer> buyer = buyerService.getBuyer();
        if (buyer.isPresent()) {
            Object delete = orderService.deleteOderOfBuyer(buyer.get(), id);
            if (delete == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(delete, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Buyer not found", HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/cart")
    public Object getCart() {
        Optional<Buyer> buyer = buyerService.getBuyer();
        if (buyer.isPresent()) {
            return new ResponseEntity<>(cartService.getCart(buyer.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>("Người mua không tồn tại", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyOnCart(@RequestBody List<Cart> carts) {
        Optional<Buyer> buyer = buyerService.getBuyer();
        if (buyer.isPresent()) {
//            if(buyer.get().getId() == orderBuyer.getId()){
            return new ResponseEntity<>(cartService.buyCart(carts), HttpStatus.OK);
//            }else {
//                return new ResponseEntity<>("Người mua không thể thay đổi giỏ hàng", HttpStatus.BAD_REQUEST);
//            }
        } else {
            return new ResponseEntity<>("Người mua không tồn tại", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/notification")
    public ResponseEntity<?> getNotification(){
        Optional<Buyer> buyer = buyerService.getBuyer();
        if(buyer.isPresent()){
            List<Notification> notificationList = notificationService.findAllByBuyer(buyer.get());
            return new ResponseEntity<>(notificationList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Người mua không tồn tại", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bill/{id}")
    public ResponseEntity<?> getBillBuyId(@PathVariable Long id){
        Optional<Buyer> buyer = buyerService.getBuyer();
        if(buyer.isPresent()){
            Bill bill = billService.checkBillOfBuyer(buyer.get(), id);
           if( bill != null){
               return new ResponseEntity<>(bill, HttpStatus.OK);
           }else return new ResponseEntity<>("Bill không tồn tại", HttpStatus.BAD_REQUEST);
        }else return new ResponseEntity<>("Người mua không tồn tại", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/bill")
    public ResponseEntity<?> getAllBillOfBuyer(){
        Optional<Buyer> buyer = buyerService.getBuyer();
        if(buyer.isPresent()){
            List<Bill> bills = billService.getAllBill(buyer.get());
            return new ResponseEntity<>(bills, HttpStatus.OK);
        }else return new ResponseEntity<>("Người mua không tồn tại", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/bill/done")
    public ResponseEntity<?> getAllBillDoneOfBuyer(){
        Optional<Buyer> buyer = buyerService.getBuyer();
        if(buyer.isPresent()){
            List<Bill> bills = billService.getAllBillDone(buyer.get());
            return new ResponseEntity<>(bills, HttpStatus.OK);
        }else return new ResponseEntity<>("Người mua không tồn tại", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/bill/cancel")
    public ResponseEntity<?> getAllBillCancelOfBuyer(){
        Optional<Buyer> buyer = buyerService.getBuyer();
        if(buyer.isPresent()){
            List<Bill> bills = billService.getAllBillCancel(buyer.get());
            return new ResponseEntity<>(bills, HttpStatus.OK);
        }else return new ResponseEntity<>("Người mua không tồn tại", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/password/{old}/{newP}")
    public ResponseEntity<?> changePassword(@PathVariable String old, @PathVariable String newP){
        Optional<Buyer> buyer = buyerService.getBuyer();
        if(buyer.isPresent()){
            List<Bill> bills = billService.getAllBillCancel(buyer.get());
            return new ResponseEntity<>(bills, HttpStatus.OK);
        }else return new ResponseEntity<>("Người mua không tồn tại", HttpStatus.BAD_REQUEST);

    }

}

