package com.spring.web.service;

import com.spring.web.model.Buyer;
import com.spring.web.model.Notification;

import java.util.List;

public interface INotificationService extends CRUDService<Notification,Long> {
    List<Notification> findAllByBuyer(Buyer buyer);
}
