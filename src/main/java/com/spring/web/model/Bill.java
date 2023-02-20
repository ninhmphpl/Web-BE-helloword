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
    private List<OrderPayment> orderPayments;

    @Column(nullable = false)
    private LocalDateTime timeBuy;

    @Transient //>> tổng giá của tất cả các order trong order list
    private Double total ;

    @Column(nullable = false)
    private Long idBuyer ;

    @Column(nullable = false)
    private String nameBuyer ;
// tổng tiển phải trả của hóa đơn
    public double totalPayment() {
        double sum = 0;
        if (orderPayments != null) {
            for (int i = 0; i < orderPayments.size(); i++) {
                sum += orderPayments.get(i).getTotalPrice();
            }
        }
        return sum;
    }


}
