package com.spring.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToMany(mappedBy = "bill")
    private List<OrderPayment> orderPayments;

    @Column(nullable = false)
    private LocalDateTime timeBuy;

    @Transient //>> tổng giá của tất cả các order trong order list
    private Double total ;

    @JsonIgnore
    @ManyToOne
    private Buyer buyer;

    @JsonIgnore
    @ManyToOne
    private Seller seller;

// tổng tiển phải trả của hóa đơn
    public double totalPayment() {
        double sum = 0;
        if (orderPayments != null && orderPayments.size() > 0) {
            for (int i = 0; i < orderPayments.size(); i++) {
                orderPayments.get(i).funtionTotalPrice();
                sum += orderPayments.get(i).getTotalPrice();
            }
        }
        return total = sum;
    }

    @ManyToOne
    private Status status;

    public Bill setBill(){
        buyer_name = buyer.getName();
        buyer_id = buyer.getId();
        seller_name = seller.getName();
        seller_id = seller.getId();
        totalPayment();
        return this;
    }
    @Transient
    String buyer_name;
    @Transient
    Long buyer_id;
    @Transient
    String seller_name;
    @Transient
    Long seller_id;



}
