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

  @ManyToOne //>> tỉnh
  @JoinColumn(name = "province_id")
  private Province province;

  @ManyToOne //>> huyện
  @JoinColumn(name = "district_id")
  private District district;

  @ManyToOne //>> xã
  @JoinColumn(name = "ward_id")
  private Ward ward;

  @Column(nullable = false)
  // Chi tiết
  private String detail;




}
