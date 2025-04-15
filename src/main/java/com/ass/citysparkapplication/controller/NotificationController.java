package com.ass.citysparkapplication.controller;

import com.ass.citysparkapplication.model.Notification;
import com.ass.citysparkapplication.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Organizer sends notification to all event participants
    @PostMapping("/event")
    public void createNotificationToEventParticipants(
            @RequestParam Integer organizerId,
            @RequestParam Integer eventId,
            @RequestParam String subject,
            @RequestParam String content
    ) {
        notificationService.createNotification(organizerId, eventId, subject, content);
    }

    // Mark one as read
    @PostMapping("/{notificationId}/read")
    public void markAsRead(@PathVariable Integer notificationId) {
        notificationService.markAsRead(notificationId);
    }

    // Get all notifications for user
    @GetMapping("/person/{personId}")
    public List<Notification> getAllNotifications(@PathVariable Integer personId) {
        return notificationService.getAllNotifications(personId);
    }

    // Get unread only
    @GetMapping("/person/{personId}/unread")
    public List<Notification> getUnreadNotifications(@PathVariable Integer personId) {
        return notificationService.getUnreadNotifications(personId);
    }
}
