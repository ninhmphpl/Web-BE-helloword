package com.spring.web.repository;

import com.spring.web.model.Category;
import com.spring.web.model.ProductSimple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductSimpleRepository extends JpaRepository<ProductSimple,Long>  {
    Page<ProductSimple> findAllByCategory(Pageable pageable, Category category);
    Page<ProductSimple> findAllByNameContaining(Pageable pageable, String name);
    Page<ProductSimple> findAllByCategoryNameContaining(Pageable pageable, String name);
    @Query(nativeQuery = true,value = "select products.* from products\n" +
            "join category on products.category_id = category.id and (products.name like :name or category.name like :name )")
    List<ProductSimple> findProductByCategoryAndName(String name );

}
