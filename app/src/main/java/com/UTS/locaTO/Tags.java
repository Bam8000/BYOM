package com.UTS.locaTO;

import java.util.ArrayList;

/**
 * Class creating the list of categories from the available events, and also allows for blacklisting of categories.
 */

public class Tags {
    private ArrayList<String> categories;
    private ArrayList<String> blacklist;

    /**
     * Constructor creating the list of categories from the given ArrayList of events.
     * @param events ArrayList of available events to create the list of categories from.
     */
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

    /**
     * Mutator method to add categories to the blacklist.
     * @param tag The specific category to add to the blacklist.
     */
    public void addToBlacklist(String tag) {
        blacklist.add(tag);
        if (categories.contains(tag)) {
            categories.remove(tag);
        }
    }

    /**
     * Mutator method updating the list of categories given a new event.
     * @param e The new event to potentially draw new categories from.
     */
    public void updateCategories(Event e) {
        for (String tag : e.getCategories()) {
            if (!this.categories.contains(tag) && !blacklist.contains(tag)) {
                categories.add(tag);
            }
        }
    }

    /**
     * String format to display the categories.
     * @return String of comma-separated categories.
     */
    public String displayCategories() {
        String s = "";
        for (int i = 0; i < categories.size() - 1; i++) {
            s += categories.get(i) + "\n";
        } s += categories.get(categories.size() - 1);
        return s;
    }

}