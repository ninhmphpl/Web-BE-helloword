package com.spring.web.service.impl;

import com.spring.web.model.*;
import com.spring.web.repository.PictureRepository;
import com.spring.web.repository.ProductDetailRepository;
import com.spring.web.service.INotificationService;
import com.spring.web.service.IProductDetailService;
import com.spring.web.service.ISellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class ProductDetailService implements IProductDetailService {
    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private INotificationService notificationService;


    @Override
    public Optional<ProductDetail> findById(Long aLong) {
        return productDetailRepository.findById(aLong);
    }

    @Override
    public List<ProductDetail> findAll() {
        return null;
    }


    /**
     * Sửa thông tin cua sản phâm và lưu nó vào database
     */
    @Override
    public ProductDetail updateProduct(ProductDetail productDetail) {
        Optional<ProductDetail> productBase = findById(productDetail.getId());
        if (productBase.isPresent()) {
            ProductDetail result = productBase.get();
            result.setName(productDetail.getName());
            result.setPrice(productDetail.getPrice());
            result.setDescription(productDetail.getDescription());
            result.setQuantity(productDetail.getQuantity());

            if (!productDetail.getPicture().equals(result.getPicture())) {
                List<Picture> pictureList = new ArrayList<>();
                for (Picture picture : productDetail.getPicture()) {
                    //>> Loại bỏ trường hợp trùng id trong database gây ra nhầm ảnh
                    picture.setId(null);
                    pictureList.add(pictureRepository.save(picture));
                }
                result.setPicture(pictureList);
            }
            result.setAvatar(result.getPicture().get(0).getName());
            result.setCategory(productDetail.getCategory());
            return productDetailRepository.save(result);
        }
        return null;
    }

    public ProductDetail updateImage(Long productId, List<Picture> pictureList) {
        Optional<ProductDetail> productRoot = productDetailRepository.findById(productId);
        return productRoot.map(productDetail -> updatePicture(pictureList, productDetail)).orElse(null);
    }
    public ProductDetail updateImage(Long productId, List<Picture> pictureList, Employee employee){
        ProductDetail productDetail = updateImage(productId, pictureList);
        if(productDetail != null){
            Notification notificationSeller = new Notification(productDetail.getSeller(),employee,productDetail, "được cập nhạt ảnh");
            notificationService.save(notificationSeller);
            return productDetail;
        }
        return null;
    }



    /**
     * Tạo 1 product detail vào database
     */
    @Override
    public ProductDetail createProduct(ProductDetail productDetail) {
        //>> lưu ảnh mới vào trong database và gán lại id sau khi lưu vào cho nó
        List<Picture> pictureList = new ArrayList<>();
        for (Picture picture : productDetail.getPicture()) {
            //>> Loại bỏ trường hợp trùng id trong database gây ra nhầm ảnh
            picture.setId(null);
            pictureList.add(pictureRepository.save(picture));
        }
        productDetail.setId(null);
        productDetail.setPicture(pictureList);
        //>> đặt avata là ảnh đầu tiên của picture
        productDetail.setAvatar(pictureList.get(0).getName());
        productDetail.setSold(0L);
        //>> mặc định status là 1 (nghĩa là mở)
        productDetail.setStatus(new Status(1L, null, null));
        return productDetailRepository.save(productDetail);
    }

    @Override
    public ProductDetail deleteProductSetStatus(Long product_id, Status status) {
        ProductDetail productDetail = productDetailRepository.findById(product_id).orElse(null);
        if (productDetail != null) {
            productDetail.setStatus(status);
            productDetail = productDetailRepository.save(productDetail);
        }
        return productDetail;
    }

    public Object createProductByEmployee(ProductDetail productDetail, Long sellerId) {
        //>> tim seller bang id va set lai seller
        Seller seller = new Seller();
        seller.setId(sellerId);
        productDetail.setSeller(seller);
        //>> lưu ảnh mới vào trong database và gán lại id sau khi lưu vào cho nó
        List<Picture> pictureList = new ArrayList<>();
        for (Picture picture : productDetail.getPicture()) {
            //>> Loại bỏ trường hợp trùng id trong database gây ra nhầm ảnh
            picture.setId(null);
            pictureList.add(pictureRepository.save(picture));
        }
        productDetail.setId(null);
        productDetail.setPicture(pictureList);
        //>> đặt avata là ảnh đầu tiên của picture
        productDetail.setAvatar(pictureList.get(0).getName());
        productDetail.setSold(0L);
        //>> mặc định status là 1 (nghĩa là mở)
        productDetail.setStatus(new Status(1L, null, null));
        return new ResponseEntity<>(productDetailRepository.save(productDetail), HttpStatus.OK);
    }

    public ProductDetail createProductForSeller(ProductDetail productDetail, Seller seller){
        productDetail.setId(null);
        productDetail.setSeller(seller);
        productDetail.setSold(0L);
        //>> mặc định status là 3 (nghĩa là đang bán)
        productDetail.setStatus(new Status(3L));
        return save(productDetail);
    }

    @Override
    public ProductDetail createProductForSeller(ProductDetail productDetail, Seller seller, Employee employee) {
        Notification notificationSeller = new Notification(seller,employee,productDetail, "được tạo");
        notificationService.save(notificationSeller);
        return createProductForSeller(productDetail, seller);
    }
    @Override
    public ProductDetail updateProductForSeller(ProductDetail productDetail, Seller seller){
        ProductDetail productRoot = getProductByListSeller(seller, productDetail.getId());
        if(productRoot != null){
            justChangePropertyUpdate(productDetail, productRoot);
            return save(productRoot);
        }
        return null;
    }

    private static void justChangePropertyUpdate(ProductDetail productDetail, ProductDetail productRoot) {
        productRoot.setName(productDetail.getName());
        productRoot.setPicture(productDetail.getPicture());
        productRoot.setCategory(productDetail.getCategory());
        productRoot.setDescription(productDetail.getDescription());
        productRoot.setPrice(productDetail.getPrice());
        productRoot.setQuantity(productDetail.getQuantity());
    }
    @Override
    public ProductDetail updateProductForSeller(ProductDetail productDetail, Employee employee){
        Optional<ProductDetail> productRoot = productDetailRepository.findById(productDetail.getId());
        if(productRoot.isPresent()){
            justChangePropertyUpdate(productDetail, productRoot.get());
            ProductDetail result = save(productRoot.get());
            Notification notificationSeller = new Notification(productRoot.get().getSeller(), employee, result, "được cập nhật");
            notificationService.save(notificationSeller);
            return result;
        }
        return null;
    }


    private ProductDetail getProductByListSeller(Seller seller, Long productId){
        for(ProductDetail productDetail : seller.getListProduct()){
            if(Objects.equals(productDetail.getId(), productId)) return productDetail;
        }
        return null;
    }


    @Override
    public ProductDetail save(ProductDetail product) {
        if (product.getName() == null) {
            product.setName("Tên Trống");
        }
        if (product.getSold() == null) {
            product.setSold(0L);
        }
        if (product.getPrice() == null) {
            product.setPrice(0D);
        }
        if (product.getPicture().size()==0) {
            product.setAvatar("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-J0cRr6ptVPnQ-DZqDJxxbjowBwIbP6l1Gsau43jbVRCWbYkB2-lC9cL1T_5WCf680pc&usqp=CAU");
        }else product.setAvatar(product.getPicture().get(0).getName());

        if (product.getCategory() == null) {
            product.setCategory(new Category(3L, null));
        }
        //>> lưu ảnh mới vào trong database và gán lại id sau khi lưu vào cho nó
        List<Picture> pictureList = new ArrayList<>(product.getPicture());
        ProductDetail productResult = productDetailRepository.save(product);
        updatePicture(pictureList, productResult);
        return productResult;
    }

    private ProductDetail updatePicture(List<Picture> pictureList, ProductDetail productResult) {
        pictureRepository.deleteAllByProductDetail(productResult);
        List<Picture> result = new ArrayList<>();
        for (Picture picture : pictureList) {
            Picture p = new Picture();
            p.setProductDetail(productResult);
            p.setName(picture.getName());
            result.add(pictureRepository.save(p));
        }
        productResult.setPicture(result);
        return productResult;
    }


    @Override
    public void delete(Long aLong) {
        productDetailRepository.deleteById(aLong);
    }

    @Override
    public Page<ProductDetail> findAllPage(Pageable pageable) {
        return productDetailRepository.findAll(pageable);
    }

    @Override
    public Page<ProductDetail> findAllPageByStatus(Pageable pageable) {
        return productDetailRepository.findAllByStatus(new Status(3L, null, null), pageable);
    }

    @Override
    public Page<ProductDetail> findAllPageAndCategory(Pageable pageable, Category category) {
        return productDetailRepository.findAllByCategory(pageable, category);
    }

    @Override
    public Page<ProductDetail> findAllPageAndNameContaining(Pageable pageable, String name) {
        return productDetailRepository.findAllByNameContaining(pageable, name);
    }

    public List<ProductDetail> findProductByPrice(Double min, Double max) {
        return productDetailRepository.findProductByPrice(min, max);
    }

    public List<ProductDetail> findProductByQuantity1(Integer min, Integer max) {
        return productDetailRepository.findProductByQuantity(min, max);
    }

    public List<ProductDetail> findAllByCategoryName(String name) {
        return productDetailRepository.findProductByCategoryAndName(name);
    }

    @Override
    public Page<ProductDetail> findAllBySeller(Seller seller, Pageable pageable) {
        return productDetailRepository.findAllBySeller(seller, pageable);
    }


}
