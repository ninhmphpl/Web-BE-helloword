package com.spring.web.repository;

import com.spring.web.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BuyerRepository extends JpaRepository<Buyer,Long> {
    List<Buyer> findAllByNameContaining(String name);
}
