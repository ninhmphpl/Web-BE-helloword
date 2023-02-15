package com.spring.web.service;

import com.spring.web.model.ProductDetail;
import com.spring.web.model.Status;

public interface IProductDetailService extends CRUDService<ProductDetail,Long>{
    ProductDetail updateProduct(ProductDetail productDetail);
    ProductDetail createProduct(ProductDetail productDetail);
    ProductDetail deleteProductSetStatus(Long product_id, Status status);
}
