package com.ass.citysparkapplication.controller;

import com.ass.citysparkapplication.model.Event;
import com.ass.citysparkapplication.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Optional<Event> getEventById(@PathVariable Integer id) {
        return eventService.getEventById(id);
    }

    @PostMapping
    public Event createEvent(@RequestParam Integer creatorPersonId, @RequestBody Event event) {
        return eventService.createEvent(event, creatorPersonId);
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Integer id, @RequestBody Event updatedEvent) {
        return eventService.updateEvent(id, updatedEvent);
    }

    @DeleteMapping("/{eventId}")
    public void deactivateEvent(@PathVariable Integer eventId, @RequestParam Integer requesterPersonId) {
        eventService.deactivateEvent(eventId, requesterPersonId);
    }

    @GetMapping("/filter")
    public List<Event> filterEvents(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(required = false) Integer tagId,
            @RequestParam(required = false) String keyword
    ) {
        return eventService.filterEvents(location, start, end, tagId, keyword);
    }


}
