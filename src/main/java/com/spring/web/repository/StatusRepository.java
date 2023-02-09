package com.spring.web.repository;

import com.spring.web.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status,Long> {
    List<Status> findAllByNameContaining(String name);
}
