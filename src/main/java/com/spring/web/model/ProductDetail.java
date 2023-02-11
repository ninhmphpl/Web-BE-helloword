package com.spring.web.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Min(0)
    private Double price;

    @Column(nullable = false)
    @Min(0)
    private Integer sold;

    @Column(nullable = false)
    private String avatar;

    @Column(nullable = false)
    private String description ;

    @Column(nullable = false)
    @Min(0)
    private Integer quantity;

    @OneToMany
    @JoinColumn(nullable = false)
    private List<Picture> picture;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Status status;

    @Column(name = "status_id")
    @Transient
    private Long statusId;

}




