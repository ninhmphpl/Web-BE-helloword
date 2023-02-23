package com.spring.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ward")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ward {
//  phường
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false, name = "_name")
  private String name;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "_district_id")
  private District district;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "_province_id")
  private Province province;


}
