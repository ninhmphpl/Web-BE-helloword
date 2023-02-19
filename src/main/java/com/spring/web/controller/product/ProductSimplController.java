package com.spring.web.controller.product;

import com.spring.web.model.Category;
import com.spring.web.model.Picture;
import com.spring.web.model.ProductDetail;
import com.spring.web.model.ProductSimple;
import com.spring.web.repository.ProductDetailRepository;
import com.spring.web.service.impl.CategoryService;
import com.spring.web.service.impl.ProductDetailService;
import com.spring.web.service.impl.ProductSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@CrossOrigin("*")
@RequestMapping("/product")
public class ProductSimplController {
    @Autowired
    private ProductSimpleService productSimpleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductDetailRepository productDetailRepository;

    @GetMapping("/filter/{name}")
    public ResponseEntity<?> searchProductBuyNamOrCategory(@PathVariable("name") String name,
                                                           @PageableDefault(value = 10)
                                                                   @SortDefault(sort = "id",direction = ASC) Pageable pageable) {
        List<ProductSimple> productSimpleList = productSimpleService.findAllByCategoryName("%" + name + "%");
//       Chuyển đổi từ List sang Page
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), productSimpleList.size());
        final Page<ProductSimple> page = new PageImpl<>(productSimpleList.subList(start, end), pageable, productSimpleList.size());
//
        if(pageable.getPageNumber() >= page.getTotalPages() || pageable.getPageNumber() < 0){
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(page , HttpStatus.OK);
    }
    @PutMapping("/edit-picture/{id}")
    public ResponseEntity<?> editImageProduct(@PathVariable("id") Long id, @RequestBody List<Picture> newImageList) {

        return new ResponseEntity<>(productDetailService.updateImage(id, newImageList),HttpStatus.BAD_REQUEST);
    }
}