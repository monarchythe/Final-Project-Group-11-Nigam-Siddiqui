package com.smu.cs.booking;

import com.smu.cs.models.Booking;
import com.smu.cs.models.BookingRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/booking")
@CrossOrigin
public class BookingController {

    // eventId -> set of booked seats
    private final Map<String, Set<String>> bookedSeats = new HashMap<>();

    // bookingId -> booking details
    private final Map<String, Booking> bookings = new HashMap<>();

    // POST /booking/book
    @PostMapping("/book")
    public Booking bookSeat(@RequestBody BookingRequest req) {

        if (req.eventId == null || req.seat == null || req.seat.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "eventId and seat are required");
        }

        bookedSeats.putIfAbsent(req.eventId, new HashSet<>());
        Set<String> seatsForEvent = bookedSeats.get(req.eventId);

        // seat already taken?
        if (seatsForEvent.contains(req.seat)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Seat already booked");
        }

        seatsForEvent.add(req.seat);

        String bookingId = UUID.randomUUID().toString();
        // username is "guest" for now, confirmed = false
        Booking booking = new Booking(bookingId, req.eventId, "guest", req.seat, false);
        bookings.put(bookingId, booking);

        return booking;  // JSON sent back to seat-selection / payment page
    }

    // GET /booking/{id}  (optional helper for debugging)
    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable String id) {
        Booking booking = bookings.get(id);
        if (booking == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
        }
        return booking;
    }
}

