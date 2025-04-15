package com.ass.citysparkapplication.repository;

import com.ass.citysparkapplication.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByPersonId(Integer personId);
    List<Notification> findByPersonIdAndStatus(Integer personId, String status);
}
