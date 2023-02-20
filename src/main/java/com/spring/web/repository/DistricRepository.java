package com.spring.web.repository;

import com.spring.web.model.District;
import com.spring.web.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DistricRepository extends JpaRepository<District,Long> {
    List<District> findDistinctByProvince (Province province);
}
