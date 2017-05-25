package com.UTS.locaTO;

import java.util.ArrayList;

/**
 * Created by Mio on 5/25/2017.
 */

public class Tags {
    private ArrayList<Event> events;
    private String title;

    public Tags (String title) {
        this.title = title;
        events = new ArrayList<Event>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public Event display() {
        for (Event event : events) return event;
        return null;
    }

}
