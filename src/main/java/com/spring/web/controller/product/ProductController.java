package com.spring.web.controller.product;

import com.spring.web.model.Picture;
import com.spring.web.model.ProductDetail;
import com.spring.web.repository.ProductDetailRepository;
import com.spring.web.service.ISellerService;
import com.spring.web.service.impl.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@CrossOrigin("*")
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ISellerService sellerService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductDetailRepository productDetailRepository;

    @GetMapping("/filter/{name}")
    public ResponseEntity<?> searchProductBuyNamOrCategory(@PathVariable("name") String name,
                                                           @PageableDefault(value = 10)
                                                           @SortDefault(sort = "id", direction = ASC) Pageable pageable) {
        List<ProductDetail> productSimpleList = productDetailService.findAllByCategoryName("%" + name + "%");
        List<ProductDetail> productDetails22 = new ArrayList<>();
        for (ProductDetail productDetail : productSimpleList) {
            boolean seller = productDetail.getSeller().getUser().getStatus().getId() == 1L;
            boolean product = productDetail.getStatus().getId() == 3L;
            if (seller && product) {
                productDetails22.add(productDetail);
            }
        }
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), productDetails22.size());
        final Page<ProductDetail> page2 = new PageImpl<>(productDetails22.subList(start, end), pageable, productDetails22.size());

        if (pageable.getPageNumber() >= page2.getTotalPages() || pageable.getPageNumber() < 0) {
            System.out.println("Page Number out range page");
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        return new ResponseEntity<>(page2, HttpStatus.OK);
    }

    /**
     * Tìm tất cả sản phẩm ở trang tùy chọn
     */
    @GetMapping("/list")
    public ResponseEntity<?> findAllPage(@PageableDefault(value = 10)
                                         @SortDefault(sort = "id", direction = DESC)
                                         Pageable pageable) {

        List<ProductDetail> productDetails = productDetailService.findAll();
        List<ProductDetail> productDetails22 = new ArrayList<>();
        for (ProductDetail productDetail : productDetails) {
            boolean seller = productDetail.getSeller().getUser().getStatus().getId() == 1L;
            boolean product = productDetail.getStatus().getId() == 3L;
            if (seller && product) {
                productDetails22.add(productDetail);
            }
        }
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), productDetails22.size());
        final Page<ProductDetail> page2 = new PageImpl<>(productDetails22.subList(start, end), pageable, productDetails22.size());

        if (pageable.getPageNumber() >= page2.getTotalPages() || pageable.getPageNumber() < 0) {
            System.out.println("Page Number out range page");
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        return new ResponseEntity<>(page2, HttpStatus.OK);
    }

    @PutMapping("/editPicture/{id}")
    public ResponseEntity<?> editImageProduct(@PathVariable("id") Long id, @RequestBody List<Picture> newImageList) {

        return new ResponseEntity<>(productDetailService.updateImage(id, newImageList), HttpStatus.OK);
    }

    @GetMapping("/seller/{id}")
    public ResponseEntity<?> findSellerByProduct(@PathVariable Long id) {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(id);
        return new ResponseEntity<>(sellerService.findByProductDetailContaining(productDetail), HttpStatus.OK);
    }
}
