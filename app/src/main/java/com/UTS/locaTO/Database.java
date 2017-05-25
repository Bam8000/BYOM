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
    private Categories categoriesList;

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
        Set<Tags> categories = new HashSet<>();
        for (Event event : events) {
            for (String tag : event.getCategories()) {
                categories.add(new Tags(tag));
            }
        } for (Tags tag : categories) {
            categoriesList.addCategory(tag);
        }
    }

    private void createTags() {
        ArrayList<Tags> tags = new HashSet<>();
        for (Event event : events) {
            for (String tag : event.getCategories()) {
                Tags tmp = new Tags(tag);
                if (!tags.contains(tmp)) {
                    tmp.addEvent(event);
                }
            }
        } for (String tag : tags) {
            categoriesList.addCategory(new Tags(tag));
        }
    }


}
