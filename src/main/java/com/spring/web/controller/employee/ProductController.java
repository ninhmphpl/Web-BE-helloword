package com.spring.web.controller.employee;

import com.spring.web.model.ProductSimple;
import com.spring.web.service.IProductSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@CrossOrigin("*")
@RequestMapping("/employees")
public class ProductController {

    @Autowired
    private IProductSimpleService productSimpleService;

    @GetMapping("/product-list")
    public ResponseEntity<?> findAllPage(@PageableDefault(value = 10)
                                             @SortDefault(sort = "id", direction = DESC)
                                             Pageable pageable){

        Page<ProductSimple> page = productSimpleService.findAllPage(pageable);

        if(pageable.getPageNumber() >= page.getTotalPages() || pageable.getPageNumber() < 0){
            System.out.println("Page Number out range page");
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        return new ResponseEntity<>(page , HttpStatus.OK);
    }


}
