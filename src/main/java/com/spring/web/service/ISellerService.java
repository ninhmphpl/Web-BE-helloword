package com.spring.web.service;

import com.spring.web.model.Employee;
import com.spring.web.model.ProductSimple;
import com.spring.web.model.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISellerService extends CRUDService<Seller , Long>{
    Page<Seller> findAllSellerPage(Pageable pageable);
    Seller findByProductSimpleContaining(ProductSimple productSimple);

}
