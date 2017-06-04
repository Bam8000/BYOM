package com.UTS.locaTO;

import java.util.ArrayList;

/**
 * Created by Mio on 5/25/2017.
 */

public class Tags {
    private ArrayList<String> categories;

    public Tags(ArrayList<Event> events) {
        categories = new ArrayList<>();
        for (Event e : events) {
            for (String tag : e.getCategories()) {
                if (!categories.contains(tag)) {
                    categories.add(tag);
                }
            }
        }
    }

    public void updateCategories(Event e) {
        for (String tag : e.getCategories()) {
            if (!this.categories.contains(tag)) {
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