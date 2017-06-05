package com.UTS.locaTO.APIs;

import android.os.AsyncTask;
import android.util.Log;

import com.UTS.locaTO.Event;
import com.UTS.locaTO.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Marcel O'Neil on 5/29/17.
 */

public class Eventbrite extends AsyncTask<Double, Void, ArrayList<Event>> {

    private MainActivity mActivity;

    public Eventbrite(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

    protected ArrayList<Event> doInBackground(Double... doubles) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("lat", doubles[0].toString());
            params.put("lng", doubles[1].toString());

            StringBuilder payload = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (payload.length() != 0) payload.append('&'); else payload.append('?');
                payload.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                payload.append('=');
                payload.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }

            Request request = new Request.Builder()
                    .url("http://192.168.2.23:3001/eventbrite/events" + payload)
                    .build();

            Response response = this.mActivity.getClient().newCall(request).execute();

            // Parse JSON
            JSONObject json = new JSONObject(response.body().string());
            JSONArray list = json.getJSONArray("events");
            for (int i = 0; i < list.length(); i++) {
                String name = list.getJSONObject(i).getJSONObject("name").getString("text");
                String venue = list.getJSONObject(i).getString("venue_id");
                
            }
            return null;
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Event> events) {
        super.onPostExecute(events);

        mActivity.onNewEvents(events);
    }

}
