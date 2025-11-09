package com.smu.booking;

import io.javalin.Javalin;
import java.util.*;

public class BookingApp {

    private static final Map<String, Booking> BOOKINGS = new HashMap<>();

    public static void main(String[] args) {
        Javalin app = Javalin.create(cfg -> cfg.http.defaultContentType = "application/json")
                .start(8083);

        app.post("/booking/book", ctx -> {
            Booking b = ctx.bodyAsClass(Booking.class);
            b.bookingId = UUID.randomUUID().toString();
            b.confirmed = false;
            BOOKINGS.put(b.bookingId, b);
            ctx.status(201).json(b);
        });

        app.post("/booking/confirm/{id}", ctx -> {
            String id = ctx.pathParam("id");
            Booking b = BOOKINGS.get(id);
            if (b == null) {
                ctx.status(404).result("{\"error\":\"Booking not found\"}");
                return;
            }
            b.confirmed = true;
            ctx.status(200).json(b);
        });

        app.get("/booking/{id}", ctx -> {
            String id = ctx.pathParam("id");
            Booking b = BOOKINGS.get(id);
            if (b == null) {
                ctx.status(404).result("{\"error\":\"Booking not found\"}");
            } else {
                ctx.json(b);
            }
        });
    }
}
