package com.spring.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
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

    @ManyToMany
    private List<Order> orderList;

    @Column(nullable = false)
    private LocalDateTime timeBuy;

    @Transient //>> tổng giá của tất cả các order trong order list
    private Double total;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Buyer buyer;



}
