package com.spring.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  @ManyToOne //>> tỉnh
  private Province province;

  @Column(nullable = false)
  @ManyToOne //>> huyện
  private District district;

  @Column(nullable = false)
  @ManyToOne //>> xã
  private Ward ward;

  @Column(nullable = false)
  // Chi tiết
  private String detail;




}
