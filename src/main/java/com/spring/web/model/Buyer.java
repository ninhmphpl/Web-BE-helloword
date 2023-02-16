package com.spring.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {
// người mua
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private LocalDate birth;

  @Column(nullable = false)
  @Pattern(regexp = "[0-9]+")
  @Length(min = 10, max = 10)
  private String phoneNumber;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Gender gender;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Status status;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Address address;


  @JoinColumn(nullable = false)
  @ManyToOne
  private User user;

  @JoinColumn(nullable = false)
  @ManyToMany
  private List<Order> cart;

 @Column(nullable = false)
  private String avatar;

  @JoinColumn(nullable = false)
  @ManyToMany
  private List<Bill> bills;


}
