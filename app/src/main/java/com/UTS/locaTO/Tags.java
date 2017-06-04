package com.UTS.locaTO;

import java.util.ArrayList;

/**
 * Created by Mio on 5/25/2017.
 */

public class Tags {
    private ArrayList<String> categories;
    private ArrayList<String> blacklist;

    public Tags(ArrayList<Event> events) {
        categories = new ArrayList<>();
        blacklist = new ArrayList<>();
        for (Event e : events) {
            for (String tag : e.getCategories()) {
                if (!categories.contains(tag)) {
                    categories.add(tag);
                }
            }
        }
    }

    public void addToBlacklist(String tag) {
        blacklist.add(tag);
        if (categories.contains(tag)) {
            categories.remove(tag);
        }
    }

    public void updateCategories(Event e) {
        for (String tag : e.getCategories()) {
            if (!this.categories.contains(tag) && !blacklist.contains(tag)) {
                categories.add(tag);
            }
        }
    }

    public String displayCategories() {
        String s = "";
        for (int i = 0; i < categories.size() - 1; i++) {
            s += categories.get(i);
        } s += categories.get(categories.size() - 1);
        return s;
    }

}