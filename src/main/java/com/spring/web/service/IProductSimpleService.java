package com.spring.web.service;

import com.spring.web.model.Category;
import com.spring.web.model.ProductSimple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductSimpleService extends CRUDService<ProductSimple, Long> {
    Page<ProductSimple> findAllPage(Pageable pageable);
    Page<ProductSimple> findAllPageAndCategory(Pageable pageable, Category category);
    Page<ProductSimple> findAllPageAndNameContaining(Pageable pageable, String name);


}
