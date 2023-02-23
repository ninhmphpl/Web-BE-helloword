package com.spring.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Role role;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Status status;

  public List<GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    return authorities;
  }
}
