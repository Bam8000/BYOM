package com.UTS.locaTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Database where all the Events are stored in one place
 * Created by Benn on 2017-05-15.
 */


public class Database {
    private ArrayList<Event> events;
    private Tags categories;

    /**
     * Constructor generating empty Database of Events
     */
    public Database() {
        this.events = new ArrayList<>();
        categories = new Tags(events);
    }

    /**
     * Mutator method adding events to the ArrayList of events
     * Also creates ArrayList of categories drawn from the categories property attached to each Event
     * @param e Event object to be added to events
     */
    public void addEvent(Event e) {
        if (!this.events.contains(e)) {
            this.events.add(e);
            categories.updateCategories(e);
        }
    }

    /**
     * Accessor method for the Database
     * @return ArrayList of events
     */
    public ArrayList<Event> getEvents() {
        return this.events;
    }

    /**
     * Accessor method for a specific event in the database of events
     * @param index The index of the event that is being retrieved from the ArrayList
     * @return The event at the specified index from the database
     */
    public Event getEvent(int index) {
        return this.events.get(index);
    }

    /**
     * Filters events to only contain the events that belong to the specific categories passed in by the ArrayList tags
     * @param tags ArrayList specifying which categories to include
     * @return ArrayList of events that only belong to the specified categories
     */
    private ArrayList<Event> filterEvents(ArrayList<String> tags) {
        ArrayList<Event> filtered = new ArrayList<>();
        for (String tag : tags) {
            for (Event e : events) {
                if (e.getCategories().contains(tag) && !filtered.contains(e)) {
                    filtered.add(e);
                }
            }
        } return filtered;
    }

    /**
     * Accessor method for categories
     * @return Tags object of categories
     */
    public Tags getCategories() {
        return categories;
    }

}
