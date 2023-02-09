package com.spring.web.service;

import java.util.List;
import java.util.Optional;

public interface CRUDService<T , ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    T save(T t);
    void delete(ID id);
}
