package com.spring.web.service;

import com.spring.web.model.Employee;
import com.spring.web.model.ProductSimple;
import com.spring.web.model.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISellerService extends CRUDService<Seller , Long>{
    Page<Seller> findAllSellerPage(Pageable pageable);
    Seller findByProductSimpleContaining(ProductSimple productSimple);
    List<Seller> finaAllSellerByNameContaining(String name);

}
