package com.spring.web.service.impl;

import com.spring.web.model.User;
import com.spring.web.service.IUserService;

import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {
    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
