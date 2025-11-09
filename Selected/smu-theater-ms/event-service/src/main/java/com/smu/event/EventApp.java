package com.smu.event;

import io.javalin.Javalin;
import java.util.*;

public class EventApp {
    private static final List<Event> EVENTS = new ArrayList<>();

    public static void main(String[] args) {
        seed();
        Javalin app = Javalin.create(cfg -> cfg.http.defaultContentType = "application/json")
                .start(8081);

        app.get("/events", ctx -> ctx.json(EVENTS));

        app.get("/events/{id}", ctx -> {
            String id = ctx.pathParam("id");
            Event e = EVENTS.stream().filter(ev -> ev.id.equals(id)).findFirst().orElse(null);
            if (e == null) ctx.status(404).result("{\"error\":\"not found\"}");
            else ctx.json(e);
        });
    }

    private static void seed() {
        if (!EVENTS.isEmpty()) return;
        EVENTS.add(new Event("e1", "Educational Movie: Exploring Space", "2025-11-20 10:00", 12));
        EVENTS.add(new Event("e2", "Recent History Documentary", "2025-11-20 13:30", 25));
        EVENTS.add(new Event("e3", "Podcast: Healthcare & Data Analytics", "2025-11-21 15:00", 30));
    }
}
