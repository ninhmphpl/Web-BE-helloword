package com.spring.web.repository;

import com.spring.web.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findAllByNameContaining(String name);
}
