package com.spring.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ProductSimple productSimple;

    @Column(nullable = false)
    private  Long amount;
    @Column(nullable = false)
    private  Long idbuyer;

    @Transient //>> tổng giá của sản phẩm hiện tại dựa trên số lượng và giá của sản phẩm
    private  Double total;

    public double getTotal() {
        return amount*productSimple.getPrice() ;
    }
}

