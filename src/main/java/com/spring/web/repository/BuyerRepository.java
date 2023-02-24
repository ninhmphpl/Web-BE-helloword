package com.spring.web.repository;

import com.spring.web.model.Buyer;
import com.spring.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer,Long> {
    Optional<Buyer> findByUser(User user);
}
