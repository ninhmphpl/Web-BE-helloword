package com.spring.web.service;

import com.spring.web.model.ProductDetail;
import com.spring.web.model.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISellerService extends CRUDService<Seller , Long>{
    Page<Seller> findAllSellerPage(Pageable pageable);
    Seller findByProductDetailContaining(ProductDetail productDetail);
    List<Seller> finaAllSellerByNameContaining(String name);

}
