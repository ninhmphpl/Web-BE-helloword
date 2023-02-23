package com.spring.web.service.impl;

import com.spring.web.model.User;
import com.spring.web.model.UserPrinciple;
import com.spring.web.repository.UserRepository;
import com.spring.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class UserService implements IUserService {
    @Autowired
    private UserRepository repository;


    public Optional<User> findById(Long aLong) {
        return repository.findById(aLong);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void delete(Long aLong) {
        repository.deleteById(aLong);

    }
    public User findByUsername(String username) {
        Optional<User> user = repository.findByUsername(username);
        return user.orElse(null);
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = repository.findByUsername(username);
//        if (!userOptional.isPresent()) {
//            throw new UsernameNotFoundException(username);
//        }
        return new UserPrinciple(userOptional.get());
    }

    @Override
    public User getUserLogging() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return findByUsername(username);
    }


}
