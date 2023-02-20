package com.spring.web.service.impl;

import com.spring.web.model.*;
import com.spring.web.repository.ProductSimpleRepository;
import com.spring.web.service.IProductSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSimpleService implements IProductSimpleService {
    @Autowired
    private ProductSimpleRepository repository;

    @Override
    public Optional<ProductSimple> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public List<ProductSimple> findAll() {
        return repository.findAll();
    }

    @Override
    public ProductSimple save(ProductSimple productSimple) {
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
    public Page<ProductSimple> findAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<ProductSimple> findAllPageAndCategory(Pageable pageable, Category category) {
        return repository.findAllByCategory(pageable, category);
    }

    @Override
    public Page<ProductSimple> findAllPageAndNameContaining(Pageable pageable, String name) {
        return repository.findAllByNameContaining(pageable, name);
    }

    public List<ProductSimple> findProductByPrice1(Double min, Double max) {
        return repository.findProductByPrice(min, max);
    }

public List<ProductSimple>findProductByQuantity1(Integer min, Integer max){
        return repository.findProductByQuantity(min,max);
}
    public List<ProductSimple> findAllByCategoryName(String name) {
        return repository.findProductByCategoryAndName(name);
    }

}

