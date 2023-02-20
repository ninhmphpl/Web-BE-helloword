package com.spring.web.repository;

import com.spring.web.model.ProductSimple;
import com.spring.web.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {
    Seller findByListProductContaining(ProductSimple productSimple);

}
