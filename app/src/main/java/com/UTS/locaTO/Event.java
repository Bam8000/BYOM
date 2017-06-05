package com.UTS.locaTO;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;



/**
 * Created by Mio on 5/17/2017.
 */

public class Event implements Comparable {
    private String eventName;
    private String address;
    private String description;
    private double cost;
    private Date time;
    private ArrayList<String> categories;
    private String url;
    private String photoUrl;

    public Event(String eventName, String address, String description, Date time, ArrayList<String> categories, double cost, String url, String photoUrl) {
        this.eventName = eventName;
        this.address = address;
        this.description = description;
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

    public String getDescription() { return description; }

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

    public String getDistance(double currLat, double currLng) throws IOException, JSONException {
        /*This may or may not work
        String dest = Uri.parse(Uri.encode(address)).toString();
        URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + currLat + "," + currLng
                + "&destinations=" + dest + "&key=AIzaSyCQvqMhyOo_BaEkHvvLuZGHeF4mlw6CUsw");

        Scanner scanner = new Scanner(url.openStream());
        String s = new String();
        while (scanner.hasNext()) {
            s += scanner.nextLine();
        }
        scanner.close();

        JSONObject object = new JSONObject(s);
            return String.valueOf(object.getJSONArray("distance").get(0));
        */
        return "1.2 km";
    }


    public String getMapUrl() {
        Uri location = Uri.parse("https://maps.google.com/maps?daddr=" + Uri.encode(address));
        return location.toString();
    }

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
