package com.spring.web.service;

import com.spring.web.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends CRUDService<User, Long>, UserDetailsService{
    public User findByUsername(String username);

}
