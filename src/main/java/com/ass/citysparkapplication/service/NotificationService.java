package com.ass.citysparkapplication.service;

import com.ass.citysparkapplication.constant.CommonConstants;
import com.ass.citysparkapplication.model.Notification;
import com.ass.citysparkapplication.model.Participation;
import com.ass.citysparkapplication.model.Person;
import com.ass.citysparkapplication.model.Event;
import com.ass.citysparkapplication.repository.NotificationRepository;
import com.ass.citysparkapplication.repository.ParticipationRepository;
import com.ass.citysparkapplication.repository.EventRepository;
import com.ass.citysparkapplication.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EventRepository eventRepository;

    // Organizer creates a broadcast to all participants of event
    public void createNotification(Integer organizerId, Integer eventId, String subject, String content) {
        Participation organizer = participationRepository.findByPersonIdAndEventId(organizerId, eventId);
        if (organizer == null || !CommonConstants.ROLE_ORGANIZER.equals(organizer.getRole()) ||
                CommonConstants.STATUS_INACTIVE.equals(organizer.getStatus())) {
            throw new RuntimeException("Only active organizers can create notifications.");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<Participation> participants = participationRepository.findByEventIdAndStatus(eventId, CommonConstants.STATUS_ACTIVE);
        for (Participation p : participants) {
            Notification n = new Notification();
            n.setPerson(p.getPerson());
            n.setEvent(event);
            n.setSubject(subject);
            n.setContent(content);
            n.setStatus(CommonConstants.STATUS_UNREAD);
            notificationRepository.save(n);
        }
    }

    // Mark notification as read
    public void markAsRead(Integer notificationId) {
        Notification n = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        n.setStatus(CommonConstants.STATUS_READ);
        notificationRepository.save(n);
    }

    // Get unread only
    public List<Notification> getUnreadNotifications(Integer personId) {
        return notificationRepository.findByPersonIdAndStatus(personId, CommonConstants.STATUS_UNREAD);
    }

    // Get all (read + unread)
    public List<Notification> getAllNotifications(Integer personId) {
        return notificationRepository.findByPersonId(personId);
    }
}
