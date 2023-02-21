package com.spring.web.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ProductDetail{
    @Column(nullable = false)
    private String description ;

    @ManyToMany
    @JoinColumn(nullable = false)
    private List<Picture> picture;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private Seller seller;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer sold;

    @Column(nullable = false)
    private String avatar;

    @Column(nullable = false)
    @Min(0)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Status status;
}




