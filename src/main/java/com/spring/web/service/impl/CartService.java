package com.spring.web.service.impl;

import com.spring.web.model.*;
import com.spring.web.model.pojo.Cart;
import com.spring.web.service.*;
import org.aspectj.weaver.ast.Or;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IBuyerService buyerService;
    
    @Autowired
    private IBillService billService;
    
    @Autowired
    private IProductDetailService productDetailService;
    
    @Autowired
    private INotificationService notificationService;
    
    public List<Cart> getCart(Buyer buyer) {
        List<Order> orders = buyer.getCart();
        orders = fillterOrder(orders);
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

    @NotNull
    private static List<Order> fillterOrder(List<Order> orders) {
        List<Order> ordersOn = new ArrayList<>();
        for(Order order : orders){
            boolean checkUser = order.getProductDetail().getSeller().getUser().getStatus().getId() == 1L;
            boolean checkProduct = order.getProductDetail().getStatus().getId() == 3L;
            if(checkProduct && checkUser ){
                ordersOn.add(order);
            }
        }
        orders = ordersOn;
        return orders;
    }

    //>> sau khi có 1 danh sách đơn hàng của buyer gửi lên,
    public List<Bill> buyCart(List<Cart> carts){
        List<Bill> bills = new ArrayList<>();
        Buyer buyer = buyerService.getBuyer().orElse(null);
        
        for (Cart cart : carts){
            Bill bill = createBill(cart.getSeller(),buyer);
            for (Order order : cart.getOrders()){

                //>> check oder co tồn tại trong cart
                if(!checkOder(order)) continue;
                //>> tạo order payment
                OrderPayment orderPayment = new OrderPayment();
                orderPayment.setProductDetail(order.getProductDetail());
                orderPayment.setAmount(order.getAmount());
                orderPayment.setTotalPrice(orderPayment.funtionTotalPrice());
                bill.getOrderPayments().add(orderPayment);
                //>>kiểm tra sản phẩm có tồn tại không,
                // sản phẩm có bị khóa không,
                // người bán sản phẩm có bị khóa không trừ số lượng của sản phẩm và cộng số lượng mua
                Optional<ProductDetail> productDetail = productDetailService.findById(order.getProductDetail().getId());
                if (checkProductExistAndOpenToSave(order, productDetail)) continue;
                //>> sau khi tạo xóa oder
                orderService.delete(order.getId());
            }
            //>> Lưu bill
            Bill billResult = billService.save(bill);
            //>> Gửi thông báo cho người bán và người mua 
            Notification notificationSeller = new Notification();
            notificationSeller.setSeller(cart.getSeller());
            notificationSeller.setName("Có 1 đơn hàng của: " + buyer.getName() + "đang chờ xử lý");
            Notification notificationBuyer = new Notification();
            notificationBuyer.setBuyer(buyer);
            notificationBuyer.setName("Đơn hàng của bạn đang chờ " + cart.getSeller().getName() + "xử lý");
            notificationService.save(notificationBuyer);
            notificationService.save(notificationSeller);
            if(billResult.getOrderPayments().size() > 0){
                bills.add(billResult);
            }

        }
        return bills;
    }

    private boolean checkProductExistAndOpenToSave(Order order, Optional<ProductDetail> productDetail) {
        if (productDetail.isPresent()){
            if (productDetail.get().getStatus().getId() != 3L){
                System.out.println("sản phẩm " +  order.getProductDetail().getId() + "bị khóa");
                return true;
            }
            if (productDetail.get().getSeller().getId() != 1L){
                System.out.println("Người bán sản phẩm " + order.getProductDetail().getId() + "đã ngừng bán");
            }
            productDetail.get().setQuantity(productDetail.get().getQuantity() - order.getAmount());
            productDetail.get().setSold(productDetail.get().getSold() + order.getAmount());
            productDetailService.save(productDetail.get());
        }else{
            System.out.println("sản phẩm: " + order.getProductDetail().getId() + "không tồn tại hoặc ngừng bán");
            return true;
        }
        return false;
    }

    private boolean checkOder(Order order){
        Buyer buyer = buyerService.getBuyer().orElse(null);
        for(Order order1 : buyer.getCart()){
            if(order1.getId() == order.getId()){
                return true;
            }
        }
        return false;
    }

    public Bill createBill(Seller seller, Buyer buyer){
        Bill bill = new Bill();
        bill.setOrderPayments(new ArrayList<>());
        bill.setStatus(new Status(5L, null, null)); //>> 5 >> đang chờ xử lý
        bill.setSeller(seller);
        bill.setBuyer(buyer);
        bill.setTimeBuy(LocalDateTime.now());
        return bill;
    }

}

