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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Marcel O'Neil on 5/29/17.
 */

public class Eventbrite extends AsyncTask<ArrayList<Double>, Void, ArrayList<Event>> {

    private MainActivity mActivity;

    public Eventbrite(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

    protected ArrayList<Event> doInBackground(ArrayList<Double>... arrayLists) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("lat", arrayLists[0].get(0).toString());
            params.put("lng", arrayLists[0].get(1).toString());

            StringBuilder payload = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (payload.length() != 0) payload.append('&');
                payload.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                payload.append('=');
                payload.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }

            Request request = new Request.Builder()
                    .url("http://192.168.2.23:3001/eventbrite" + payload)
                    .build();

            Response response = this.mActivity.getClient().newCall(request).execute();

            // Parse JSON
            JSONObject json = new JSONObject(response.body().string());
            JSONArray list = json.getJSONObject("data").getJSONArray("children");
            JSONObject recent = list.getJSONObject(0).getJSONObject("data");
            String currentDate = new SimpleDateFormat("dd-MMMM-yyyy", Locale.CANADA).format(new Date());
            String redditDate = recent.getString("title").split(" - ")[0];
            if (currentDate.equalsIgnoreCase(redditDate)) {
                String[] items = recent.getString("selftext").split("\\n\\n");
                ArrayList<Event> events = new ArrayList<Event>();
                Log.i("APIs.Reddit", "Updating events from /u/torontothingstodo");
                for (String item : items) {
                    String title = item.replaceAll("(.*\\[)|(\\].*)", "");
                    String link = item.replaceAll("(.*\\()|(\\).*)", "");
                    events.add(new Event(title, null, null, null, null, 0, link));
                }
                return events;
            } else {
                Log.w("APIs.Reddit", "/u/torontothingstodo has not yet posted today's events");
                return null;
            }
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
