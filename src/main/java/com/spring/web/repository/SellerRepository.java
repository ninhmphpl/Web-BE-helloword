package com.spring.web.repository;

import com.spring.web.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {
    Seller findByName(String username);
//    Seller findByUsername(String username);

}
