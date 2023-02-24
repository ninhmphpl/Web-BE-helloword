package com.spring.web.service.impl;

import com.spring.web.model.*;
import com.spring.web.repository.BuyerRepository;
import com.spring.web.repository.OrderPaymentRepository;
import com.spring.web.repository.ProductDetailRepository;
import com.spring.web.service.IAddressService;
import com.spring.web.service.IBuyerService;
import com.spring.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private BillService billService;

    @Autowired
    private OrderPaymentRepository orderPaymentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAddressService addressService;


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
        Optional<Buyer> buyer = getBuyer();
        Optional<ProductDetail> productDetail = productDetailRepository.findById(id);
        if (productDetail.isPresent() && buyer.isPresent()) {

            if (productDetail.get().getStatus().getId() != 1L ){
                return new ResponseEntity<>("Sản phẩm này hiện đã ngừng kinh doanh", HttpStatus.BAD_REQUEST);
            }
            for (Order order : buyer.get().getCart()){
                if(productDetail.get().getId() == order.getProductDetail().getId()){
                    order.setAmount(order.getAmount() + quantity);
                    return orderService.save(order);
                }
            }
            Order order = new Order(null, productDetail.get(), quantity, 0D, buyer.get());
            return orderService.save(order);
        }


//        if (buyer.isPresent()) {
//            List<Order> orderLists = buyer.get().getCart();
//            Long orderId = 0L;
//            if (orderLists.size() != 0) {
//                boolean flag = true;
//                for (Order cart : orderLists) {
//                    if (cart.getProductDetail().getId().equals(id)) {
//                        orderId = cart.getId();
//                        Order order = orderService.findById(orderId).get();
//                        Long amount = order.getAmount();
//                        order.setAmount(amount + quantity);
//                        orderService.save(order);
//                        flag = false;
//                    }
//                }
//                if (flag) {
//
//                    Order newOrder = new Order(null, productDetailRepository.findById(id).get(), quantity, null, null);
//                    Order orderCreated = orderService.save(newOrder);
//                    orderLists.add(orderCreated);
//                    buyer.get().setCart(orderLists);
//                    repository.save(buyer.get());
//                }
//            } else {
//                Order newOrder = new Order(null, productDetailRepository.findById(id).get(), quantity, null, null);
//                Order oderCreate = orderService.save(newOrder);
//                buyer.get().getCart().add(oderCreate);
//                repository.save(buyer.get());
//            }
//
//            return repository.findById(1L).get().getCart();
//        } else {
//            return "403,không có người dùng";
//        }
        return new ResponseEntity<>("Người dùng hoặc sản phẩm không tồn tại", HttpStatus.BAD_REQUEST);
    }

    public ResultCheck checkOrderQuantity(Order order) {
        Optional<Buyer> buyer = repository.findById(1L);
        if (buyer.isPresent()) {
            Long idProduct = order.getProductDetail().getId();
            Long stock = productDetailRepository.findById(idProduct).get().getQuantity();
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
            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setId(orders.get(i).getId());
            orderPayment.setProductDetail(orders.get(i).getProductDetail());
            orderPayment.setAmount(orders.get(i).getAmount());
            orderPayment.setTotalPrice(orders.get(i).getTotal());
            orderPaymentRepository.save(orderPayment);
            orderPayments.add(orderPayment);

        }

        Buyer buyer = repository.findById(1L).get();
        List<Bill> billList = buyer.getBills();
        Bill newBill = new Bill();
        newBill.setOrderPayments(orderPayments);
        newBill.setTimeBuy(LocalDateTime.now());
        newBill.setBuyer(buyer);
        newBill.setNameBuyer(buyer.getName());
        newBill.setTotal(newBill.totalPayment());
        newBill = billService.save(newBill);
        billList.add(newBill);
        buyer.setBills(billList);


        for (Order order : orders) {
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
        Long idProduct = order.getProductDetail().getId();
        ProductDetail productDetail = productDetailRepository.findById(idProduct).get();
        Long soldOld = productDetail.getSold();
        Long soldNew = order.getAmount();
        Long stock = productDetail.getQuantity();
        productDetail.setSold(soldNew + soldOld);
        productDetail.setQuantity(stock - soldNew);
        productDetailRepository.save(productDetail);
    }

    /// sửa lại giỏ hàng sau khi thanh toán
    public void deleteCartAfterPay(Order order) {
        orderService.delete(order.getId());
    }

    @Override
    public Object createBuyer(Buyer buyer, String username, String password) {
        Iterable<Buyer> buyers = repository.findAll();
        for (Buyer buyer1 : buyers) {
            if (buyer1.getUser().getUsername().equals(username)) {
                return new ResponseEntity<>("Username đã tồn tại trong hệ thống", HttpStatus.BAD_REQUEST);
            }
        }
        buyer.setId(null);
        User user1 = new User();
        user1.setRole(new Role(4L, null));
        user1.setStatus(new Status(1L, null, null));
        user1.setId(null);
        user1.setUsername(username);
        user1.setPassword(passwordEncoder.encode(password));
        user1 = userService.save(user1);
        Address address1 = addressService.save(buyer.getAddress());
        buyer.setAddress(address1);
        buyer.setUser(user1);
        buyer.setAvatar("https://th.bing.com/th/id/R.06703a8fbf9fc3f5883d874e8dfb098f?rik=FWCoLrReRMwU1Q&pid=ImgRaw&r=0");
        buyer.setDescription(" Không có thông tin");
        return repository.save(buyer);
    }

    @Override
    public Optional<Buyer> getBuyer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        return repository.findByUser(user);
    }
}
