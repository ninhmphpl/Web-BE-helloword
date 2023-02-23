package com.spring.web.controller;

import com.spring.web.model.Category;
import com.spring.web.model.Gender;
import com.spring.web.service.ICategoryService;
import com.spring.web.service.IGenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/gender")
public class GenderController {

    @Autowired
    private IGenderService service;

    @GetMapping
    public ResponseEntity<?> findAllCategory(){
        List<Gender> listCategory = service.findAll();
        return new ResponseEntity<>(listCategory, HttpStatus.OK);
    }
}
