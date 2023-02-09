package com.spring.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AddressRepository extends JpaRepository<AddressRepository,Long> {
    List<AddressRepository> findAllByNameContaining(String name);
}
