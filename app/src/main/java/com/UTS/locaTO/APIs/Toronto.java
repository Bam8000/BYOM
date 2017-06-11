package com.UTS.locaTO.APIs;

import android.os.AsyncTask;
import android.util.Log;

import com.UTS.locaTO.Event;
import com.UTS.locaTO.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Toronto Open Data API
 */

public class Toronto extends AsyncTask<Void, Void, ArrayList<Event>> {

    private final MainActivity mActivity;

    /**
     * Constructor for Toronto Open Data API
     * @param mActivity context
     */
    public Toronto(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * Async http call
     * @param voids Void
     * @return Event List
     */
    protected ArrayList<Event> doInBackground(Void... voids) {
        try {
            Request request = new Request.Builder()
                    .url("http://192.168.2.23:3001/toronto")
                    .build();

            Response response = this.mActivity.getClient().newCall(request).execute();

            // Parse JSON
            Log.i("APIs.Toronto", "Updating events from Toronto Open Data Catalogue");
            JSONArray list = new JSONArray(response.body().string());
            ArrayList<Event> events = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.CANADA);
            for (int i = 0; i < list.length(); i++) {
                JSONObject json = list.getJSONObject(i);
                String name = json.getString("name");
                String description = json.getString("description");
                String location = json.getString("location");
                String timeString = json.getString("time");
                Date time = formatter.parse(timeString.replaceAll("Z$", "+0000"));
                JSONArray arr = json.getJSONArray("categories");
                ArrayList<String> categories = new ArrayList<>();
                for(int j = 0; j < arr.length(); j++){
                    categories.add(arr.getString(j));
                }
                Double cost = json.getDouble("cost");
                String url = json.getString("url");
                String photo = json.getString("photo");
                events.add(new Event(name, location, description, time, categories, cost, url, photo));
            }
            return events;
        } catch (IOException | JSONException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Event> events) {
        super.onPostExecute(events);

        mActivity.onNewEvents(events);
    }
}