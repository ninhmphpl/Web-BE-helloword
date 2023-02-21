package com.spring.web.model;

import com.spring.web.model.pojo.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.TypeCache;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    private ProductDetail productDetail;

    @Column(nullable = false)
    private Long amount;

    @Transient //>> tổng giá của sản phẩm hiện tại dựa trên số lượng và giá của sản phẩm
    private Double total;

    public double getTotal() {
        return amount * productDetail.getPrice();
    }
}

