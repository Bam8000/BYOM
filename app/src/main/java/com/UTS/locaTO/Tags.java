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

    public String getTitle() {
        return title;
    }

    public ArrayList<Event> display() {
        return events;
    }

    public String toString() {
        String s = title;
        for (Event event : events) {
            s += event + "\n";
        } return s;
    }

}
