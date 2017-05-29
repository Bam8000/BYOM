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

    public Database() {
        this.events = new ArrayList<Event>();
    }


    public void addEvent(Event e) {
        if (!this.events.contains(e)) {
            this.events.add(e);
        }
    }

    public ArrayList<Event> getEvents() {
        return this.events;
    }

    public Event getEvent(int index) {
        return this.events.get(index);
    }


    //change looks good!
}
