package com.UTS.locaTO;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mio on 5/17/2017.
 */

public class Event implements Comparable {
    private String eventName;
    private String location;
    private double cost;
    private Date time;
    private ArrayList<String> keywords;
    private ArrayList<String> categories;
    private String url;

    public Event(String eventName, String location,  Date time, ArrayList<String> keywords, ArrayList<String> categories, double cost, String url) {
        this.eventName = eventName;
        this.location = location;
        this.time = time;
        this.keywords = keywords;
        this.categories = categories;
        this.cost = cost;
        this.url = url;
    }

    public String getEventName() {
        return eventName;
    }

    public String getLocation() {
        return location;
    }

    public Date getTime() {
        return time;
    }

    public double getCost() {
        return cost;
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<String> getCategories() { return categories; }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    @Override
    public int compareTo(Object obj) {
        Date d = (Date) obj;
        if (time.after(d)) {
            return -1;
        } else if (time.before(d)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Event) {
            Event e = (Event) obj;
            if (e.getEventName().equals(eventName) && e.getLocation().equals(location) && e.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Event name: " + eventName + "\nLocation: " + location + "\nDate: " + time + "\nCost: " + cost + "\nLink: " + url;
    }


}
