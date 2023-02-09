package com.spring.web.repository;

import com.spring.web.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface PositionRepository extends JpaRepository<Position,Long> {
    List<Position> findAllByNameContaining(String name);
}
