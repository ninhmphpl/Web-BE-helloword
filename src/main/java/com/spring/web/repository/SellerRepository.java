package com.spring.web.repository;

import com.spring.web.model.ProductDetail;
import com.spring.web.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {
    Seller findByListProductContaining(ProductDetail productSimple);
    List<Seller> findAllByNameContaining(String name);

}
