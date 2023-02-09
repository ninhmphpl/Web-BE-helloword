package com.spring.web.repository;

import com.spring.web.model.ProductSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSimpleRepository extends JpaRepository<ProductSimple,Long > {
}
