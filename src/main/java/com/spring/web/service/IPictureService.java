package com.spring.web.service;

import com.spring.web.model.Picture;
import com.spring.web.model.ProductDetail;

public interface IPictureService extends CRUDService<Picture, Long> {
    Picture updatePicture(Picture picture);
}
