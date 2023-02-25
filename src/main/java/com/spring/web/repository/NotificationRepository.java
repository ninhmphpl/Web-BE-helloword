package com.spring.web.repository;

import com.spring.web.model.Buyer;
import com.spring.web.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByBuyerOrderByIdDesc(Buyer buyer);
}
