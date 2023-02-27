package com.spring.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.utility.nullability.MaybeNull;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    private Seller seller;

    @JsonIgnore
    @ManyToOne
    private Buyer buyer;

    @JsonIgnore
    @ManyToOne
    private Employee employee;

    @JsonIgnore
    @ManyToOne
    private  Admin admin;

    @JsonIgnore
    @ManyToOne
    private Bill bill;

    @Transient
    private String url;

    @JsonIgnore
    @ManyToOne
    private ProductDetail productDetail;

    public Notification(Seller seller, Bill bill) {
        this.seller = seller;
        this.bill = bill;
        this.name = "Đơn hàng " + bill.getId() + " đã được " + bill.getStatus().getName();
    }

    public Notification(Buyer buyer, Bill bill) {
        this.buyer = buyer;
        this.bill = bill;
        this.name = "Đơn hàng " + bill.getId() + " đã được người bán " + bill.getSeller().getName() + " " + bill.getStatus().getName();
    }

    public Notification(Seller seller, Employee employee, ProductDetail productDetail, String note){
        this.seller = seller;
        this.employee = employee;
        this.productDetail = productDetail;
        this.name = "Sản phẩm " + productDetail.getName() + " " + note + " bởi nhân viên " + employee.getName();
    }

    public String getURL(){
        if(bill != null){
            url = "/bill/" + bill.getId();
        }
        return null;
    }


}
