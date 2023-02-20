package com.spring.web.controller.seller;

import com.spring.web.model.Picture;
import com.spring.web.service.IProductDetailService;
import com.spring.web.service.IProductSimpleService;
import com.spring.web.service.impl.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin("*")
@RequestMapping("/product")
public class ProductSellerController {
    @Autowired
    private IProductSimpleService productSimpleService;

    @Autowired
    private IProductDetailService productDetailService;
    @Autowired
    private PictureService pictureService;
    @PutMapping("/edit-picture/{along}")
    public ResponseEntity<Picture>edit(@RequestBody Picture picture ){
        Picture picture1 = pictureService.updatePicture(picture);
        return new ResponseEntity<>(picture1, HttpStatus.OK);
    }
}
