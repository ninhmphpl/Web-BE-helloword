package com.spring.web.controller.employee;

import com.spring.web.model.*;
;
import com.spring.web.service.IBuyerService;
import com.spring.web.service.IEmployeeService;
import com.spring.web.service.IProductDetailService;
import com.spring.web.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@CrossOrigin("*")
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private IProductDetailService productDetailService;
    @Autowired
    private ISellerService sellerService;
    @Autowired
    private IEmployeeService employeeService;

    /**
     * Tìm tất cả sản phẩm ở trang tùy chọn
     */
    @GetMapping("/product-list")
    public ResponseEntity<?> findAllPage(@PageableDefault(value = 10)
                                         @SortDefault(sort = "id", direction = DESC)
                                         Pageable pageable) {

        Page<ProductDetail> page = productDetailService.findAllPageByStatus(pageable);

        if (pageable.getPageNumber() >= page.getTotalPages() || pageable.getPageNumber() < 0) {
            System.out.println("Page Number out range page");
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * Tìm sản phẩm theo id của nó
     */
    @GetMapping("/product-detail/{along}")
    public ResponseEntity<?> findOne(@PathVariable Long along) {
        Optional<ProductDetail> productDetail = productDetailService.findById(along);
        if (productDetail.isPresent()) {
            return new ResponseEntity<>(productDetail.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    /**
     * Tao sản phẩm
     */
    @PostMapping("/product-create")
    public ResponseEntity<ProductDetail> create(@RequestBody ProductDetail productDetail) {
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
    public Object delete(@PathVariable("along") Long along) {
        Optional<ProductDetail> productDetail = productDetailService.findById(along);
        if (productDetail.isPresent()) {
            ProductDetail result = productDetailService.deleteProductSetStatus(along, new Status(4L, null, null));
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return "404, không tìm thấy sản phẩm";
    }

    /**
     * Tìm tất cả product theo category id
     * Và hển thị theo trang
     */
    @GetMapping("/list/category/{id}")
    public ResponseEntity<?> findAllProductDetailAndCategory(@PathVariable Long id,
                                                             @PageableDefault(value = 10)
                                                             @SortDefault(sort = "id", direction = DESC) Pageable pageable) {
        Page<ProductDetail> productSimples = productDetailService.findAllPageAndCategory(pageable, new Category(id, null));
        return new ResponseEntity<>(productSimples, HttpStatus.OK);
    }

    /**
     * Tìm tất cả product theo name
     * Hiển thị theo trang
     */
    @GetMapping("/product-list/search")
    public ResponseEntity<?> findAllProductDetailAndSearch(@RequestParam(name = "search") String search,
                                                           @PageableDefault(value = 10) Pageable pageable) {
        Page<ProductDetail> productSimples = productDetailService.findAllPageAndNameContaining(pageable, search);
        return new ResponseEntity<>(productSimples, HttpStatus.OK);
    }


    @GetMapping("/price/{priceMin}/{priceMax}")
    public ResponseEntity<?> findByProductPrice(@PathVariable Double priceMin, @PathVariable Double priceMax,
                                                @PageableDefault(value = 10) Pageable pageable) {
        List<ProductDetail> productSimpleList = productDetailService.findProductByPrice(priceMin, priceMax);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), productSimpleList.size());
        final Page<ProductDetail> page = new PageImpl<>(productSimpleList.subList(start, end), pageable, productSimpleList.size());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    @GetMapping("/search-quantity/{quantityMin}/{quantityMax}")
    public ResponseEntity<?> findByProductQuantity(@PathVariable Integer quantityMin, @PathVariable Integer quantityMax,
                                                   @PageableDefault(value = 10) Pageable pageable) {
        List<ProductDetail> productSimpleList = productDetailService.findProductByQuantity1(quantityMin, quantityMax);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), productSimpleList.size());
        final Page<ProductDetail> page = new PageImpl<>(productSimpleList.subList(start, end), pageable, productSimpleList.size());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping("/create-product/{id}")
    public ResponseEntity<?> createProduct(@RequestBody ProductDetail productDetail,
                                @PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployee();
        if(employee.isPresent()){
            Optional<Seller> seller = sellerService.findById(id);
            if (seller.isPresent()){
                ProductDetail productResult = productDetailService.createProductForSeller(productDetail, seller.get(), employee.get());
                return new ResponseEntity<>(productResult, HttpStatus.OK);
            }else return new ResponseEntity<>("404, Người bán này không còn tồn tại trong hệ thống", HttpStatus.BAD_REQUEST);
        }return new ResponseEntity<>("Nhân viên không tồn tại", HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/product")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDetail productDetail){
        Optional<Employee> employee = employeeService.getEmployee();
        if (employee.isPresent()) {
            ProductDetail productResult = productDetailService.updateProductForSeller(productDetail, employee.get());
            return new ResponseEntity<>(productResult, HttpStatus.OK);
        } else return new ResponseEntity<>("Nhân viên không tồn tại", HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/editPicture/{id}")
    public ResponseEntity<?> editPictureProduct(@RequestBody List<Picture> pictures,@PathVariable Long id){
        Optional<Employee> employee = employeeService.getEmployee();
        if (employee.isPresent()) {
            ProductDetail productResult = productDetailService.updateImage(id, pictures, employee.get());
            return new ResponseEntity<>(productResult, HttpStatus.OK);
        } else return new ResponseEntity<>("Nhân viên không tồn tại", HttpStatus.BAD_REQUEST);
    }
}
