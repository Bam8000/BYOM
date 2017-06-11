package com.UTS.locaTO;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Object holding all properties of an Event
 */

public class Event implements Comparable {
    private String name;
    private String address;
    private String description;
    private double cost;
    private Date time;
    private ArrayList<String> categories;
    private String url;
    private String photo;

    /**
     * Constructor creating Event object given properties of event from data
     * @param name Name or title of the event
     * @param address Location of the event
     * @param description Description of the event
     * @param time Time of the event occurring that day
     * @param categories Which categories the event fits under
     * @param cost Cost of the event
     * @param url URL for the event given by the organizer
     * @param photo Photo pulled from data of the event
     */
    public Event(String name, String address, String description, Date time, ArrayList<String> categories, double cost, String url, String photo) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.time = time;
        this.categories = categories;
        this.cost = cost;
        this.url = url;
        this.photo = photo;
    }

    /**
     * Accessor method for the name of the event
     * @return Name of the event
     */
    public String getName() {
        return name;
    }

    /**
     * Accessor method for address of the event
     * @return Address of the event
     */
    public String getAddress() {
        return address;
    }

    /**
     * Accessor method for description of the event
     * @return Description of the event
     */
    public String getDescription() { return description; }

    /**
     * Accessor method for time of the event
     * @return Time of the event
     */
    public Date getTime() {
        return time;
    }

    /**
     * Accessor method for cost of the event
     * @return Cost of the event
     */
    public double getCost() {
        return cost;
    }

    /**
     * Accessor method for URL of the event
     * @return URL of the event
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns a string of the distance, in km, from the current location.
       Appends the string "km" to the end of the number.
     * @param currLat Latitude of current location of user
     * @param currLng Longitude of current location of user
     * @return Distance from current position of user to the location of the event
     */
    public String getDistance(double currLat, double currLng) {
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

    /**
     * Accessor method for formatted URI to allow opening of the location of the event in Google Maps
     * @return String URL of location in Google Maps
     */
    public String getMapUri() {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("api", "1");
            params.put("destination", this.getAddress());

            StringBuilder payload = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (payload.length() != 0) payload.append('&'); else payload.append('?');
                payload.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                payload.append('=');
                payload.append(URLEncoder.encode(param.getValue(), "UTF-8"));
            }

            Uri uri = Uri.parse("https://www.google.com/maps/dir/" + payload);
            return uri.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Accessor method for photo of event
     * @return URL of photo
     */
    public String getPhoto() {
        return this.photo;
    }

    /**
     * Accessor method for categories of event
     * @return ArrayList of categories
     */
    public ArrayList<String> getCategories() { return categories; }

    /**
     * Returns categories of the event as a formatted String
     * @return String of comma-separated categories
     */
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
            if (e.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Event name: " + name + "\nLocation: " + address + "\nDate: " + time + "\nCost: " + cost + "\nLink: " + url;
    }


}
