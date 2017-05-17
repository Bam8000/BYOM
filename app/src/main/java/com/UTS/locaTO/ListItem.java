package com.UTS.locaTO;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yammi on 5/17/2017.
 */

public class ListItem {
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
}
