package com.spring.web.repository;

import com.spring.web.model.Picture;
import com.spring.web.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    void deleteAllByProductDetail(ProductDetail productDetail);
}
