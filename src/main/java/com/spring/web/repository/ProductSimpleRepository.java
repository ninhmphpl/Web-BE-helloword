package com.spring.web.repository;

import com.spring.web.model.Category;
import com.spring.web.model.ProductSimple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSimpleRepository extends JpaRepository<ProductSimple,Long> {
    Page<ProductSimple> findAllByCategory(Pageable pageable, Category category);
}
