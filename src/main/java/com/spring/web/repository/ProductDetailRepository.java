package com.spring.web.repository;

import com.spring.web.model.Category;
import com.spring.web.model.ProductDetail;
import com.spring.web.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    Page<ProductDetail> findAllByCategory(Pageable pageable, Category category);

    Page<ProductDetail> findAllByNameContaining(Pageable pageable, String name);
    Page<ProductDetail> findAllByCategoryNameContaining(Pageable pageable, String name);
    @Query(nativeQuery = true,value = "select products.* from products\n" +
            "join category on products.category_id = category.id and (products.name like :name or category.name like :name )")
    List<ProductDetail> findProductByCategoryAndName(String name );

    @Query(nativeQuery = true, value = "select * from products a where( a.price>= :min)and\n" +
            "( a.price<= :max)")
    List<ProductDetail> findProductByPrice(Double min, Double max);

    @Query(nativeQuery = true, value = "select *from products a where (a.quantity>= :min)and\n" +
            "(a.quantity<= :max)")
    List<ProductDetail> findProductByQuantity(Integer min,Integer max);

    Page<ProductDetail> findAllByStatus(Status status, Pageable pageable);

}
