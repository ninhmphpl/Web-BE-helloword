package com.spring.web.controller.product;

import com.spring.web.model.Category;
import com.spring.web.model.ProductSimple;
import com.spring.web.service.impl.CategoryService;
import com.spring.web.service.impl.ProductSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@CrossOrigin("*")
@RequestMapping("/product")
public class ProductSimplController {
    @Autowired
    private ProductSimpleService productSimpleService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/filter/{name}")
    public ResponseEntity<?> searchProductBuyNamOrCategory(@PathVariable("name") String name,
                                                           @PageableDefault(value = 10)
                                                                   @SortDefault(sort = "id",direction = ASC) Pageable pageable) {
        Page<ProductSimple> page = productSimpleService.findAllPageAndNameContaining(pageable,name);
        Page<ProductSimple> page1 = productSimpleService.findAllByCategoryName(pageable,name);

        if(pageable.getPageNumber() >= page.getTotalPages() || pageable.getPageNumber() < 0){
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        return new ResponseEntity<>(page1 , HttpStatus.OK);
    }
}
