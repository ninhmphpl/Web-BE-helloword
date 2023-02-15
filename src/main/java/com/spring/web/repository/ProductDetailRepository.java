package com.spring.web.repository;

import com.spring.web.model.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    ProductDetail findProductDetailByNameContaining(String name);

    // dùng JPQL query
    @Query("select a from ProductDetail a " +
            "where " +
            "(:keyword is null or a.name like %:keyword%) and " +
            "(:fromQuantity is null or a.quantity >= :fromQuantity) and " +
            "(:toQuantity is null or a.quantity <= :toQuantity) and " +
            "(:fromPrice is null or a.price >= :fromPrice) and " +
            "(:toPrice is null or a.price <= :toPrice)")
    Page<ProductDetail> getListProduct(String keyword,
                                       Integer fromQuantity,
                                       Integer toQuantity,
                                       Double fromPrice,
                                       Double toPrice,
                                       Pageable pageable);
// Dùng native query
    @Query(value = "select * from products a " +
            "where " +
            "(:keyword is null or a.name like %:keyword%) and " +
            "(:fromQuantity is null or a.quantity >= :fromQuantity) and " +
            "(:toQuantity is null or a.quantity <= :toQuantity) and " +
            "(:fromPrice is null or a.price >= :fromPrice) and " +
            "(:toPrice is null or a.price <= :toPrice)",
            countQuery = "select count(*) from products a " +
                    " where " +
                    " (:keyword is null or a.name like %:keyword%) and " +
                    "  (:fromQuantity is null or a.quantity >= :fromQuantity) and " +
                    "  (:toQuantity is null or a.quantity <= :toQuantity) and " +
                    "   (:fromPrice is null or a.price >= :fromPrice) and " +
                    "   (:toPrice is null or a.price <= :toPrice)", nativeQuery = true)
    Page<ProductDetail> getListProduct1(String keyword,
                                        Integer fromQuantity,
                                        Integer toQuantity,
                                        Double fromPrice,
                                        Double toPrice,
                                        Pageable pageable);
}
