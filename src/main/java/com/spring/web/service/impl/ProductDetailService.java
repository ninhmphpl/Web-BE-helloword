package com.spring.web.service.impl;

import com.spring.web.model.Picture;
import com.spring.web.model.ProductDetail;
import com.spring.web.model.Status;
import com.spring.web.repository.PictureRepository;
import com.spring.web.repository.ProductDetailRepository;
import com.spring.web.service.IProductDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@Slf4j
@Transactional
public class ProductDetailService implements IProductDetailService {

    @Autowired
    private ProductDetailRepository repository;
    @Autowired
    private PictureRepository pictureRepository;


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
        Optional<ProductDetail> productBase = findById(productDetail.getId());
        if (productBase.isPresent()) {
            ProductDetail result = productBase.get();
            result.setName(productDetail.getName());
            result.setPrice(productDetail.getPrice());
            result.setDescription(productDetail.getDescription());
            result.setQuantity(productDetail.getQuantity());

            if (!productDetail.getPicture().equals(result.getPicture())) {
                List<Picture> pictureList = new ArrayList<>();
                for (Picture picture : productDetail.getPicture()){
                    //>> Loại bỏ trường hợp trùng id trong database gây ra nhầm ảnh
                    picture.setId(null);
                    pictureList.add(pictureRepository.save(picture));
                }
                result.setPicture(pictureList);
            }
            result.setAvatar(result.getPicture().get(0).getName());
            productDetail.setCategory(productDetail.getCategory());
            return repository.save(result);
        }
        return null;
    }

    /**
     * Tạo 1 product detail vào database
     */
    @Override
    public ProductDetail createProduct(ProductDetail productDetail) {
        //>> lưu ảnh mới vào trong database và gán lại id sau khi lưu vào cho nó
        List<Picture> pictureList = new ArrayList<>();
        for (Picture picture : productDetail.getPicture()){
            //>> Loại bỏ trường hợp trùng id trong database gây ra nhầm ảnh
            picture.setId(null);
            pictureList.add(pictureRepository.save(picture));
        }
        productDetail.setId(null);
        productDetail.setPicture(pictureList);
        //>> đặt avata là ảnh đầu tiên của picture
        productDetail.setAvatar(pictureList.get(0).getName());
        productDetail.setSold(0);
        //>> mặc định status là 1 (nghĩa là mở)
        productDetail.setStatus(new Status(1L, null));
        return  repository.save(productDetail);
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
