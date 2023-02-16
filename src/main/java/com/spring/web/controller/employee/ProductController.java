package com.spring.web.controller.employee;

import com.spring.web.model.*;
import com.spring.web.service.IProductDetailService;
import com.spring.web.service.IProductSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@CrossOrigin("*")
@RequestMapping("/employees")
public class ProductController {

    @Autowired
    private IProductSimpleService productSimpleService;

    @Autowired
    private IProductDetailService productDetailService;

    /**
     * Tìm tất cả sản phẩm ở trang tùy chọn
     */
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

    /**
     * Tìm sản phẩm theo id của nó
     */
    @GetMapping("/product-detail/{along}")
    public ResponseEntity<?> findOne(@PathVariable Long along) {
        Optional<ProductDetail> productDetail=productDetailService.findById(along);
        if (productDetail.isPresent()){
            return new ResponseEntity<>(productDetail.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    /**
     * Tao sản phẩm
     */
    @PostMapping("/product-create")
    public ResponseEntity<ProductDetail> create(@RequestBody ProductDetail productDetail){
        ProductDetail productResult = productDetailService.createProduct(productDetail);
        return new ResponseEntity<>(productResult, HttpStatus.OK);
    }
    /**
     * Sửa thông tin sản phẩm
     */
    @PutMapping("/product-edit/{along}")
    public ResponseEntity<ProductDetail> edit(@RequestBody ProductDetail productDetail) {
        ProductDetail productDetail2 = productDetailService.updateProduct(productDetail);
        return new ResponseEntity<>(productDetail2, HttpStatus.OK);
    }

    /**
     * Xóa 1 sản phẩm bằng cách sửa trạng thái của nó về startus có id = 2
     */
    @DeleteMapping("/product-delete/{along}")
    public ResponseEntity<ProductDetail> delete(@PathVariable("along") Long along) {
        Optional<ProductDetail> productDetail = productDetailService.findById(along);
        if (productDetail.isPresent()) {
            ProductDetail result = productDetailService.deleteProductSetStatus(along, new Status(3L, null,null));
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    /**
     * Tìm tất cả product theo category id
     * Và hển thị theo trang
     */
    @GetMapping("/prodcut-list/category/{id}")
    public ResponseEntity<?> findAllProductSimpleAndCategory (@PathVariable Long id,
                                                   @PageableDefault(value = 10)
                                                    @SortDefault(sort = "id", direction = DESC) Pageable pageable){
        Page<ProductSimple> productSimples = productSimpleService.findAllPageAndCategory(pageable, new Category(id,null));
        return new ResponseEntity<>(productSimples, HttpStatus.OK);
    }
    /**
     * Tìm tất cả product theo name
     * Hiển thị theo trang
     */
    @GetMapping("/product-list/search")
    public ResponseEntity<?> findAllProductSimpleAndSearch (@RequestParam(name = "search") String search,
                                                   @PageableDefault(value = 10) Pageable pageable){
        Page<ProductSimple> productSimples = productSimpleService.findAllPageAndNameContaining(pageable, search);
        return new ResponseEntity<>(productSimples, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search (@RequestBody SearchRequest request,
                                     @PageableDefault(value = 10) Pageable pageable) {
        Page<ProductDetail> page = productDetailService.search(request, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }





}
