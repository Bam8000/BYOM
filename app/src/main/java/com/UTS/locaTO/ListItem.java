package com.UTS.locaTO;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mio on 5/17/2017.
 */

public class ListItem implements Comparable {
    private String eventName;
    private String location;
    private double cost;
    private Date time;
    private ArrayList<String> keywords;
    private String url;

    public ListItem(String eventName, String location,  Date time, ArrayList<String> keywords, double cost, String url) {
        this.eventName = eventName;
        this.location = location;
        this.time = time;
        this.keywords = keywords;
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
        if (obj instanceof ListItem) {
            ListItem e = (ListItem) obj;
            if (e.getEventName().equals(eventName) && e.getLocation().equals(location) && e.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }


}
