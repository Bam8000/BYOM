package com.UTS.locaTO;

import android.support.annotation.NonNull;

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

    public String getEventLocation() {
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

    /*
    Returns a string of the distance, in km, from the current location.
    Appends the string "km" to the end of the number.
     */
    public String getDistance() {
        //TODO

    }


    /*Are categories and keywords separate things? I think it's a few too many different items for the user to process.
    I'm just gonna display the categories.
     */
    public ArrayList<String> getCategories() { return categories; }

    public String stringCategories() {
        String str = "";
        for (int i=0; i<categories.size(); i++) {
            str += categories.get(i);
            if (i != categories.size()-1) {
                str += ", ";
            }
        }
        return str;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public String displayKeywords() {
        String s = "";
        for (int i = 0; i < keywords.size() - 1; i++) {
            s += keywords.get(i) + ", ";
        } s += keywords.get(keywords.size() - 1);
        return s;
    }

    @Override
    public int compareTo(@NonNull Object obj) {
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
            if (e.getEventName().equals(eventName) && e.getEventLocation().equals(location) && e.getTime().equals(time)) {
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
