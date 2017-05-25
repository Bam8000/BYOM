package com.UTS.locaTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Benn on 2017-05-15.
 */


public class Database {
    private ArrayList<Event> events;

    public Database(ArrayList<Event> events) {
        this.events = events;
        updateEvents();
    }

    public void addEvent(Event e) {
        events.add(e);
        updateEvents();
    }

    private void updateEvents() {
        Collections.sort(events);
        int i = 0;
        while (i < events.size()-1) {
            if (events.get(i).equals(events.get(i+1))) {
                events.remove(i+1);
            } else {
                i++;
            }
        }
    }

    public void createCategories() {
        Set<Categories> categories = new HashSet<>();
        for (Event event : events) {
            for (String tag : event.getCategories()) {
                categories.add(new Tags(tag));

            }
        }
    }


}
