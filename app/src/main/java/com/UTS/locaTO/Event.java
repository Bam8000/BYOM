package com.UTS.locaTO;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapsInitializer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Math.round;

/**
 * Created by Mio on 5/17/2017.
 */

public class Event implements Comparable {
    private String eventName;
    private String address;
    private double cost;
    private Date time;
    private ArrayList<String> categories;
    private String url;
    private String photoUrl;

    public Event(String eventName, String address,  Date time, ArrayList<String> categories, double cost, String url, String photoUrl) {
        this.eventName = eventName;
        this.address = address;
        this.time = time;
        this.categories = categories;
        this.cost = cost;
        this.url = url;
        this.photoUrl = photoUrl;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventLocation() {
        return address;
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
    /*public String getDistance(Location currLocation) {
        float dist = currLocation.distanceTo(location);
        DecimalFormat df = new DecimalFormat("###.0");
        return df.format(dist) + " km";
    }
    */
    //Here's the method, Marcel for you to do. Using it in EventsAdapter.
    public String getPhotoUrl() {
        //TODO
        return null;
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
            if (e.getEventName().equals(eventName) && e.getEventLocation().equals(address) && e.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Event name: " + eventName + "\nLocation: " + address + "\nDate: " + time + "\nCost: " + cost + "\nLink: " + url;
    }


}
