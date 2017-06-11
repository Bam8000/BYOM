package com.UTS.locaTO.APIs;

import android.os.AsyncTask;
import android.util.Log;

import com.UTS.locaTO.Event;
import com.UTS.locaTO.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Reddit API
 */

public class Reddit extends AsyncTask<Void, Void, ArrayList<Event>> {

    private final MainActivity mActivity;

    /**
     * Constructor for Reddit API
     * @param mActivity context
     */
    public Reddit(MainActivity mActivity) {
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
                    .url("https://www.reddit.com/user/torontothingstodo/submitted.json")
                    .build();

            Response response = this.mActivity.getClient().newCall(request).execute();

            // Parse JSON
            JSONObject json = new JSONObject(response.body().string());
            JSONArray list = json.getJSONObject("data").getJSONArray("children");
            JSONObject recent = list.getJSONObject(0).getJSONObject("data");
            String currentDate = new SimpleDateFormat("d-MMM-yyyy", Locale.CANADA).format(new Date());
            String redditDate = recent.getString("title").split(" - ")[0];
            if (currentDate.equalsIgnoreCase(redditDate)) {
                String[] items = recent.getString("selftext").split("\\n\\n");
                ArrayList<Event> events = new ArrayList<>();
                Log.i("APIs.Reddit", "Updating events from /u/torontothingstodo");
                for (String item : items) {
                    String title = item.replaceAll("(.*\\[)|(\\].*)", "");
                    String link = item.replaceAll("(.*\\()|(\\).*)", "");
                    Date date = new Date();
                    events.add(new Event(title, "", "", date, new ArrayList<String>(), 0, link, ""));
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
