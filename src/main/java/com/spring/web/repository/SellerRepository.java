package com.spring.web.repository;

import com.spring.web.model.ProductDetail;
import com.spring.web.model.Seller;
import com.spring.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {
    Seller findByListProductContaining(ProductDetail productSimple);
    List<Seller> findAllByNameContaining(String name);
    Optional<Seller> findByUser(User user);

}
