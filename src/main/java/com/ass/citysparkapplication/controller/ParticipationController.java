package com.ass.citysparkapplication.controller;

import com.ass.citysparkapplication.model.Participation;
import com.ass.citysparkapplication.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    @PostMapping("/register")
    public void register(@RequestParam Integer personId, @RequestParam Integer eventId) {
        participationService.registerForEvent(personId, eventId);
    }

    @PostMapping("/unregister")
    public void unregister(@RequestParam Integer personId, @RequestParam Integer eventId) {
        participationService.unregisterFromEvent(personId, eventId);
    }

    @GetMapping("/person/{personId}")
    public List<Participation> getEventsByPerson(@PathVariable Integer personId) {
        return participationService.getActiveParticipationsByPerson(personId);
    }

    @GetMapping("/event/{eventId}")
    public List<Participation> getPeopleByEvent(@PathVariable Integer eventId) {
        return participationService.getActiveParticipationsByEvent(eventId);
    }

    @PostMapping("/promote")
    public void promoteToOrganizer(
            @RequestParam Integer promoterId,
            @RequestParam Integer targetId,
            @RequestParam Integer eventId
    ) {
        participationService.promoteToOrganizer(promoterId, targetId, eventId);
    }

}
