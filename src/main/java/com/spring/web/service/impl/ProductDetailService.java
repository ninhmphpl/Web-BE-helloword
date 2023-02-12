package com.spring.web.service.impl;

import com.spring.web.model.ProductDetail;
import com.spring.web.model.Status;
import com.spring.web.repository.ProductDetailRepository;
import com.spring.web.service.IProductDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Slf4j
public class ProductDetailService implements IProductDetailService {

    @Autowired
    ProductDetailRepository repository;
    @Override
    public Optional<ProductDetail> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public List<ProductDetail> findAll() {
        return null;
    }

    @Override
    public ProductDetail save(ProductDetail productDetail) {
        return repository.save(productDetail);
    }

    @Override
    public void delete(Long aLong) {
    }

    /**
     * Sửa thông tin cua sản phâm và lưu nó vào database
     */
    @Override
    public ProductDetail updateProduct(ProductDetail productDetail){
        ProductDetail productDetail1= findById(productDetail.getId()).get();
        productDetail1.setName(productDetail.getName());
        productDetail1.setPrice(productDetail.getPrice());
        productDetail1.setSold(productDetail.getSold());
        productDetail1.setAvatar(productDetail.getAvatar());
        productDetail1.setDescription(productDetail.getDescription());
        productDetail1.setQuantity(productDetail.getQuantity());
        productDetail1.setPicture(productDetail.getPicture());
        productDetail.setCategory(productDetail.getCategory());
        return repository.save(productDetail1);

    }

    @Override
    public ProductDetail deleteProductSetStatus(Long product_id,Status status) {
        ProductDetail productDetail= repository.findById(product_id).orElse(null);
        if (productDetail != null) {
            productDetail.setStatus(status);
            productDetail = repository.save(productDetail);
        }
        return productDetail;
    }
}
