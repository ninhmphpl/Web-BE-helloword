package com.spring.web.service;

import com.spring.web.model.ProductDetail;
import com.spring.web.model.SearchRequest;
import com.spring.web.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductDetailService extends CRUDService<ProductDetail,Long>{
    ProductDetail updateProduct(ProductDetail productDetail);
    ProductDetail createProduct(ProductDetail productDetail);
    ProductDetail deleteProductSetStatus(Long product_id, Status status);

//    Page<ProductDetail> search(SearchRequest request, Pageable pageable);
}
