package com.spring.web.service;

import com.spring.web.model.ProductSimple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductSimpleService extends CRUDService<ProductSimple, Long> {
    Page<ProductSimple> findAllPage(Pageable pageable);
}
