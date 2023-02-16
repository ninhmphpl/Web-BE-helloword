package com.spring.web.service.impl;

import com.spring.web.model.Seller;
import com.spring.web.model.User;
import com.spring.web.repository.AddressRepository;
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
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RoleService roleService;

    @Override
    public Optional<User> findById(Long aLong) {
        return repository.findById(aLong);
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
    public User findByUsername(String username) {
        return repository.findUserByUsername(username);
    }
    public boolean signUp(User users) {
                users.setRole(roleService.findById(2l).get());
                repository.save(users);
                return true;
            }
    public boolean checkUserExist(User user) {
        List<User> users = repository.findAll();
        for (User u : users) {
            if (user.getUsername().equals(u.getUsername())) {
                return true;
            }
        }
        return false;
    }
}
