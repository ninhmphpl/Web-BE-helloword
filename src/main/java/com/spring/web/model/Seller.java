package com.spring.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
//  người bán
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  @Pattern(regexp = "[0-9]+")
  @Length(min = 10, max = 10)
  private String phoneNumber;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Address address;

  @JsonIgnore
  @OneToMany(mappedBy = "seller")
  private List<ProductDetail> listProduct;

  @Column(nullable = false)
  private String description;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(nullable = false)
  private User user;

  @JsonIgnore
  @OneToMany(mappedBy = "seller")
  private List<Bill> bill;

  @OneToMany(mappedBy = "seller")
  private List<Notification> notifications;


}
