package com.spring.web.service.adminActive;
import com.spring.web.model.User;
import com.spring.web.service.CRUDService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeDisableService implements CRUDService<User, Long> {
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
