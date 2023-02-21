package com.spring.web.service.impl;

import com.spring.web.model.*;
import com.spring.web.repository.PictureRepository;
import com.spring.web.repository.ProductDetailRepository;
import com.spring.web.service.IProductDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    /**
     * Sửa thông tin cua sản phâm và lưu nó vào database
     */
    @Override
    public ProductDetail updateProduct(ProductDetail productDetail) {
        Optional<ProductDetail> productBase = findById(productDetail.getId());
        if (productBase.isPresent()) {
            ProductDetail result = productBase.get();
            result.setName(productDetail.getName());
            result.setPrice(productDetail.getPrice());
            result.setDescription(productDetail.getDescription());
            result.setQuantity(productDetail.getQuantity());

            if (!productDetail.getPicture().equals(result.getPicture())) {
                List<Picture> pictureList = new ArrayList<>();
                for (Picture picture : productDetail.getPicture()) {
                    //>> Loại bỏ trường hợp trùng id trong database gây ra nhầm ảnh
                    picture.setId(null);
                    pictureList.add(pictureRepository.save(picture));
                }
                result.setPicture(pictureList);
            }
            result.setAvatar(result.getPicture().get(0).getName());
            result.setCategory(productDetail.getCategory());
            return repository.save(result);
        }
        return null;
    }

    public Object updateImage(Long id, List<Picture> pictureList) {
        Optional<ProductDetail> productBase = repository.findById(id);
        if (productBase.isPresent()) {
            ProductDetail result = productBase.get();
            List<Picture> list = result.getPicture();
            List<Picture> newpictureList = new ArrayList<>();
            for (Picture picture : pictureList) {
                picture.setId(null);
                newpictureList.add(pictureRepository.save(picture));
            }
            result.setPicture(newpictureList);
            result.setAvatar(result.getPicture().get(0).getName());
            return repository.save(result);
        }
        return ("Không tim thấy sản phẩm");

    }


    /**
     * Tạo 1 product detail vào database
     */
    @Override
    public ProductDetail createProduct(ProductDetail productDetail) {
        //>> lưu ảnh mới vào trong database và gán lại id sau khi lưu vào cho nó
        List<Picture> pictureList = new ArrayList<>();
        for (Picture picture : productDetail.getPicture()) {
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
        productDetail.setStatus(new Status(1L, null, null));
        return repository.save(productDetail);
    }

    @Override
    public ProductDetail deleteProductSetStatus(Long product_id, Status status) {
        ProductDetail productDetail = repository.findById(product_id).orElse(null);
        if (productDetail != null) {
            productDetail.setStatus(status);
            productDetail = repository.save(productDetail);
        }
        return productDetail;
    }

    public Object createProductByEmployee(ProductDetail productDetail, Long sellerId) {
        //>> tim seller bang id va set lai seller
        Seller seller = new Seller();
        seller.setId(sellerId);
        productDetail.setSeller(seller);
        //>> lưu ảnh mới vào trong database và gán lại id sau khi lưu vào cho nó
        List<Picture> pictureList = new ArrayList<>();
        for (Picture picture : productDetail.getPicture()) {
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
        productDetail.setStatus(new Status(1L, null, null));
        return new ResponseEntity<>(repository.save(productDetail), HttpStatus.OK);
    }



    @Override
    public ProductDetail save(ProductDetail productSimple) {
        if (productSimple.getName() == null) {
            productSimple.setName("Tên Trống");
        }
        if (productSimple.getSold() == null) {
            productSimple.setSold(0);
        }
        if (productSimple.getPrice() == null) {
            productSimple.setPrice(0D);
        }
        if (productSimple.getAvatar() == null) {
            productSimple.setAvatar("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-J0cRr6ptVPnQ-DZqDJxxbjowBwIbP6l1Gsau43jbVRCWbYkB2-lC9cL1T_5WCf680pc&usqp=CAU");
        }
        if (productSimple.getCategory() == null) {
            productSimple.setCategory(new Category(1L, null));
        }
        return repository.save(productSimple);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
    public Page<ProductDetail> findAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<ProductDetail> findAllPageByStatus(Pageable pageable) {
        return repository.findAllByStatus(new Status(1L, null, null), pageable);
    }

    @Override
    public Page<ProductDetail> findAllPageAndCategory(Pageable pageable, Category category) {
        return repository.findAllByCategory(pageable, category);
    }

    @Override
    public Page<ProductDetail> findAllPageAndNameContaining(Pageable pageable, String name) {
        return repository.findAllByNameContaining(pageable, name);
    }

    public List<ProductDetail> findProductByPrice(Double min, Double max) {
        return repository.findProductByPrice(min, max);
    }

    public List<ProductDetail> findProductByQuantity1(Integer min, Integer max) {
        return repository.findProductByQuantity(min, max);
    }

    public List<ProductDetail> findAllByCategoryName(String name) {
        return repository.findProductByCategoryAndName(name);
    }


}
