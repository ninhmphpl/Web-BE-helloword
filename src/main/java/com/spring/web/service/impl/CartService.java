package com.spring.web.service.impl;

import com.spring.web.model.*;
import com.spring.web.model.pojo.Cart;
import com.spring.web.service.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    //>> sau khi có 1 danh sách đơn hàng của buyer gửi lên,
    public Bill buyCart(List<Cart> carts){
        Buyer buyer = buyerService.getBuyer().orElse(null);
//        Notification notification = new Notification();
//        notification.setSeller(carts.get(0).getSeller());
//        notification.setName("Bạn có 1 đơn hàng từ người mua" + buyer.getName());
        
        for (Cart cart : carts){
            Bill bill = new Bill();
            bill.setOrderPayments(new ArrayList<>());
            bill.setStatus(new Status(5L, null, null)); //>> 5 >> đang chờ xử lý
            bill.setSeller(cart.getSeller());
            bill.setBuyer(buyer);
            
            for (Order order : cart.getOrders()){
                //>> tạo order payment
                OrderPayment orderPayment = new OrderPayment();
                orderPayment.setProductDetail(order.getProductDetail());
                orderPayment.setAmount(order.getAmount());
                orderPayment.setTotalPrice(orderPayment.funtionTotalPrice());

                bill.getOrderPayments().add(orderPayment);
                //>> trừ số lượng của sản phẩm và cộng số lượng mua
                Optional<ProductDetail> productDetail = productDetailService.findById(order.getProductDetail().getId());
                if(productDetail.isPresent()){
                    productDetail.get().setQuantity(productDetail.get().getQuantity() - order.getAmount());
                    productDetail.get().setSold(productDetail.get().getSold() + order.getAmount());
                    productDetailService.save(productDetail.get());
                }else{
                    System.out.println("sản phẩm: " + order.getProductDetail().getId() + "không tồn tại");
                    return null;
                }
                //>> xóa oder
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
            return billResult;
        }
        return null;
    }


}

