package com.spring.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressRepository,Long> {
    List<AddressRepository> findAllByNameContaining(String name);
}
