package com.smu.cs.models;

public class Booking {
    public String bookingId;
    public String eventId;
    public String username;
    public String seat;
    public boolean confirmed;

    public Booking(String bookingId, String eventId, String username, String seat, boolean confirmed) {
        this.bookingId = bookingId;
        this.eventId = eventId;
        this.username = username;
        this.seat = seat;
        this.confirmed = confirmed;
    }
}
