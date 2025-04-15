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

import java.util.List;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EventRepository eventRepository;

    public void registerForEvent(Integer personId, Integer eventId) {
        Participation existing = participationRepository.findByPersonIdAndEventId(personId, eventId);
        if (existing != null) {
            existing.setStatus(CommonConstants.STATUS_ACTIVE);
            participationRepository.save(existing);
            return;
        }

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Participation participation = new Participation();
        participation.setPerson(person);
        participation.setEvent(event);
        participation.setRole(CommonConstants.ROLE_ATTENDEE);
        participation.setStatus(CommonConstants.STATUS_ACTIVE);

        participationRepository.save(participation);
    }

    public void unregisterFromEvent(Integer personId, Integer eventId) {
        Participation existing = participationRepository.findByPersonIdAndEventId(personId, eventId);
        if (existing == null || CommonConstants.STATUS_INACTIVE.equals(existing.getStatus())) return;

        if (CommonConstants.ROLE_ORGANIZER.equals(existing.getRole())) {
            long organizerCount = participationRepository.findByEventIdAndStatus(eventId, CommonConstants.STATUS_ACTIVE)
                    .stream()
                    .filter(p -> CommonConstants.ROLE_ORGANIZER.equals(p.getRole()))
                    .count();

            if (organizerCount <= 1) {
                throw new RuntimeException("Cannot unregister. You are the only active organizer.");
            }
        }

        existing.setStatus(CommonConstants.STATUS_INACTIVE);
        participationRepository.save(existing);
    }


    public List<Participation> getActiveParticipationsByPerson(Integer personId) {
        return participationRepository.findByPersonIdAndStatus(personId, CommonConstants.STATUS_ACTIVE);
    }

    public List<Participation> getActiveParticipationsByEvent(Integer eventId) {
        return participationRepository.findByEventIdAndStatus(eventId, CommonConstants.STATUS_ACTIVE);
    }

    public void promoteToOrganizer(Integer promoterPersonId, Integer targetPersonId, Integer eventId) {
        Participation promoter = participationRepository.findByPersonIdAndEventId(promoterPersonId, eventId);
        if (promoter == null || !CommonConstants.ROLE_ORGANIZER.equals(promoter.getRole()) ||
                CommonConstants.STATUS_INACTIVE.equals(promoter.getStatus())) {
            throw new RuntimeException("Only active organizers can promote others.");
        }

        Participation target = participationRepository.findByPersonIdAndEventId(targetPersonId, eventId);
        if (target == null || CommonConstants.STATUS_INACTIVE.equals(target.getStatus())) {
            throw new RuntimeException("Target must be an active participant to be promoted.");
        }

        target.setRole(CommonConstants.ROLE_ORGANIZER);
        participationRepository.save(target);
    }

}
