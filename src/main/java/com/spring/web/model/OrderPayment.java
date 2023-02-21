package com.spring.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ProductDetail productDetail;

    @Column(nullable = false)
    private  Long amount;

    @Transient //>> tổng giá của sản phẩm hiện tại dựa trên số lượng và giá của sản phẩm
    private  Double totalPrice;

    public double funtionTotalPrice() {
        return amount*productDetail.getPrice() ;
    }
}
