package com.spring.web.service.impl;

import com.spring.web.model.User;
import com.spring.web.repository.UserRepository;
import com.spring.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class UserService implements IUserService {
    @Autowired
    private UserRepository repository;
    @Override
    public Optional<User> findById(Long aLong) {
        return repository.findById();
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);

    }
}
