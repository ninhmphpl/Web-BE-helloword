package com.spring.web.repository;

import com.spring.web.model.Category;
import com.spring.web.model.ProductSimple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSimpleRepository extends JpaRepository<ProductSimple,Long>  {
    Page<ProductSimple> findAllByCategory(Pageable pageable, Category category);
    Page<ProductSimple> findAllByNameContaining(Pageable pageable, String name);
    Page<ProductSimple> findAllByCategoryNameContaining(Pageable pageable, String name);


    @Query(nativeQuery = true,value = "SELECT * FROM products WHERE name LIKE concat('%',:name,'%') AND category_name LIKE concat('%',:name,'%') ")
    Page<ProductSimple> findProductByCategoryAndName(String name );

}
