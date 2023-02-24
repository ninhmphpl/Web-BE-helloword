package com.spring.web.service.impl;

import com.spring.web.model.Notification;
import com.spring.web.repository.NotificationRepository;
import com.spring.web.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private NotificationRepository repository;
    @Override
    public Optional<Notification> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public List<Notification> findAll() {
        return repository.findAll();
    }

    @Override
    public Notification save(Notification notification) {
        return repository.save(notification);
    }

    @Override
    public void delete(Long aLong) {
        repository.findById(aLong);
    }
}
