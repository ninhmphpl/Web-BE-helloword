package com.spring.web.service;

import com.spring.web.model.Bill;
import com.spring.web.model.ProductDetail;
import com.spring.web.model.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ISellerService extends CRUDService<Seller , Long>{
    Page<Seller> findAllSellerPage(Pageable pageable);
    Seller findByProductDetailContaining(ProductDetail productDetail);
    List<Seller> finaAllSellerByNameContaining(String name);

    Object create(Seller seller, String username, String password);
    Optional<Seller> getSeller();
    boolean checkProductIdOfSeller(Long productId, Seller seller);

    ProductDetail changeStatusProduct(Seller seller, Long productId, Long statusId);
    List<Bill> getAllBillSortDesc(Seller seller);
    List<Bill> getAllByStatus(Seller seller, long statusId);


}
