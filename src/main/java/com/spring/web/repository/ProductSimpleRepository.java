package com.spring.web.repository;

import com.spring.web.model.Category;
import com.spring.web.model.ProductSimple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSimpleRepository extends JpaRepository<ProductSimple, Long> {
    Page<ProductSimple> findAllByCategory(Pageable pageable, Category category);

    Page<ProductSimple> findAllByNameContaining(Pageable pageable, String name);

    @Query(nativeQuery = true, value = "select * from products a where( a.price>= :min)and\n" +
            "( a.price<= :max)")
    List<ProductSimple> findProductByPrice(Double min, Double max);

    @Query(nativeQuery = true, value = "select *from products a where (a.quantity>= :min)and\n" +
            "(a.quantity<= :max)")
    List<ProductSimple> findProductByQuantity(Integer min,Integer max);

}
