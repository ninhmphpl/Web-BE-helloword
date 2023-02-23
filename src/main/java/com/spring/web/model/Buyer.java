package com.spring.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
  private Address address;

  @JsonIgnore
  @JoinColumn(nullable = false)
  @ManyToOne
  private User user;

  @OneToMany(mappedBy = "buyer")
  private List<Order> cart;

 @Column(nullable = false)
  private String avatar;

  @OneToMany(mappedBy = "buyer")
  private List<Bill> bills;

  @Column(nullable = false)
  private String description;

}
