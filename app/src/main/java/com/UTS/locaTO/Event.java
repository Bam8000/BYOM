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
 * Created by Mio on 5/17/2017.
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

    public String getName() {
        return name;
    }

    public String getAddress() {
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

    //Here's the method, Marcel for you to do. Using it in EventsAdapter.
    public String getPhoto() {
        //TODO
        return this.photo;
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
