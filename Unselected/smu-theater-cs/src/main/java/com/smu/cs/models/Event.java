package com.smu.cs.models;

public class Event {
    public String id;
    public String title;
    public String dateTime;
    public int seatsAvailable;

    public Event(String id, String title, String dateTime, int seatsAvailable) {
        this.id = id;
        this.title = title;
        this.dateTime = dateTime;
        this.seatsAvailable = seatsAvailable;
    }
}
