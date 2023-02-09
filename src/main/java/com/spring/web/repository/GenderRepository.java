package com.spring.web.repository;

import com.spring.web.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GenderRepository extends JpaRepository<Gender,Long> {
}
