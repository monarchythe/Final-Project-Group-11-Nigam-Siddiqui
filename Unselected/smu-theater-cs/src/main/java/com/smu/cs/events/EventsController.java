package com.smu.cs.events;

import com.smu.cs.models.Event;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@CrossOrigin
public class EventsController {

    private final List<Event> events = List.of(
            new Event("e1", "Educational Movie: Exploring", "2025-11-20 10:00", 12),
            new Event("e2", "Recent History Documentary", "2025-11-21 13:30", 25),
            new Event("e3", "Podcast: Healthcare & Data", "2025-11-22 15:00", 30)
    );

    // GET /events  -> list all events
    @GetMapping
    public List<Event> getAllEvents() {
        return events;
    }

    // GET /events/{id}  -> single event
    @GetMapping("/{id}")
    public Event getEventById(@PathVariable String id) {
        return events.stream()
                .filter(ev -> ev.id.equals(id))
                .findFirst()
                .orElse(null);
    }
}
