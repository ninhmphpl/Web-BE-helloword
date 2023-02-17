package com.spring.web.service.impl;

import com.spring.web.model.Category;
import com.spring.web.repository.CategoryRepository;
import com.spring.web.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository repository;
    @Override
    public Optional<Category> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Category save(Category category) {
        return repository.save(category);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);
    }

    public  List<Category> findCategorybyName(String name){
        return repository.findAllByNameContaining(name);
    }
}
