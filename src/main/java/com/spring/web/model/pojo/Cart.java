package com.spring.web.model.pojo;


import com.spring.web.model.Order;
import com.spring.web.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    private Seller seller;
    private List<Order> orders;
}
