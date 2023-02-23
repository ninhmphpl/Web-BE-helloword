package com.spring.web.service;

import com.spring.web.model.Category;
import com.spring.web.model.ProductDetail;
import com.spring.web.model.SearchRequest;
import com.spring.web.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductDetailService extends CRUDService<ProductDetail,Long>{
    ProductDetail updateProduct(ProductDetail productDetail);
    ProductDetail createProduct(ProductDetail productDetail);
    ProductDetail deleteProductSetStatus(Long product_id, Status status);

    Page<ProductDetail> findAllPage(Pageable pageable);
    Page<ProductDetail> findAllPageByStatus(Pageable pageable);

    Page<ProductDetail> findAllPageAndCategory(Pageable pageable, Category category);
    Page<ProductDetail> findAllPageAndNameContaining(Pageable pageable, String name);
    public List<ProductDetail> findProductByPrice(Double min, Double max);
    public List<ProductDetail> findProductByQuantity1(Integer min, Integer max);
    public List<ProductDetail> findAllByCategoryName(String name);
}
