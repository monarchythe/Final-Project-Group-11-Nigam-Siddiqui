package com.smu.event;

public class Event {
    public String id;
    public String title;
    public String dateTime;      // keeping it as string for demo
    public int seatsAvailable;

    public Event(String id, String title, String dateTime, int seatsAvailable) {
        this.id = id;
        this.title = title;
        this.dateTime = dateTime;
        this.seatsAvailable = seatsAvailable;
    }
}
