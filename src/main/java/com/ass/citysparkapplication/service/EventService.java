package com.ass.citysparkapplication.service;

import com.ass.citysparkapplication.constant.CommonConstants;
import com.ass.citysparkapplication.model.Event;
import com.ass.citysparkapplication.model.Participation;
import com.ass.citysparkapplication.model.Person;
import com.ass.citysparkapplication.repository.EventRepository;
import com.ass.citysparkapplication.repository.ParticipationRepository;
import com.ass.citysparkapplication.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findByStatus(CommonConstants.STATUS_ACTIVE);
    }

    public Optional<Event> getEventById(Integer id) {
        return eventRepository.findById(id)
                .filter(event -> CommonConstants.STATUS_ACTIVE.equals(event.getStatus()));
    }

    public Event createEvent(Event event, Integer creatorPersonId) {
        event.setStatus(CommonConstants.STATUS_ACTIVE);
        Event savedEvent = eventRepository.save(event);

        Person creator = personRepository.findById(creatorPersonId)
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        Participation organizer = new Participation();
        organizer.setEvent(savedEvent);
        organizer.setPerson(creator);
        organizer.setRole(CommonConstants.ROLE_ORGANIZER);
        organizer.setStatus(CommonConstants.STATUS_ACTIVE);
        participationRepository.save(organizer);

        return savedEvent;
    }

    public Event updateEvent(Integer id, Event updatedEvent) {
        return eventRepository.findById(id).map(event -> {
            event.setTitle(updatedEvent.getTitle());
            event.setStatus(CommonConstants.STATUS_ACTIVE);
            event.setDescription(updatedEvent.getDescription());
            event.setLocation(updatedEvent.getLocation());
            event.setEventStartDt(updatedEvent.getEventStartDt());
            event.setEventEndDt(updatedEvent.getEventEndDt());
            return eventRepository.save(event);
        }).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public void deactivateEvent(Integer eventId, Integer requesterPersonId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Participation requester = participationRepository.findByPersonIdAndEventId(requesterPersonId, eventId);
        if (requester == null || !CommonConstants.ROLE_ORGANIZER.equals(requester.getRole()) ||
                CommonConstants.STATUS_INACTIVE.equals(requester.getStatus())) {
            throw new RuntimeException("Only an active organizer can deactivate the event.");
        }

        // Mark event as inactive
        event.setStatus(CommonConstants.STATUS_INACTIVE);
        eventRepository.save(event);

        // Mark all participations as inactive
        List<Participation> participations = participationRepository.findByEventIdAndStatus(eventId, CommonConstants.STATUS_ACTIVE);
        for (Participation p : participations) {
            p.setStatus(CommonConstants.STATUS_INACTIVE);
        }
        participationRepository.saveAll(participations);
    }


    public List<Event> filterEvents(String location, LocalDateTime start, LocalDateTime end, Integer tagId, String keyword) {
        return eventRepository.filterEvents(location, start, end, tagId, keyword);
    }


    public List<Event> getEventsByTagId(Integer tagId) {
        return eventRepository.findByTagId(tagId);
    }
}
