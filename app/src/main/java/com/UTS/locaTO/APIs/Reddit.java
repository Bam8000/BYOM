package com.UTS.locaTO.APIs;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Marcel O'Neil on 5/11/17.
 */

public class Reddit extends AsyncTask<Void, Void, String[]> {

    private OkHttpClient client = new OkHttpClient();

    protected String[] doInBackground(Void... voids) {

        Request request = new Request.Builder()
                .url("https://www.reddit.com/user/torontothingstodo/submitted.json")
                .build();
        try {
            Response response = this.client.newCall(request).execute();

            // Parse JSON
            JSONObject json = new JSONObject(response.body().string());
            JSONArray list = json.getJSONObject("data").getJSONArray("children");
            JSONObject recent = list.getJSONObject(0).getJSONObject("data");
            String currentDate = new SimpleDateFormat("dd-MMMM-yyyy", Locale.CANADA).format(new Date());
            String redditDate = recent.getString("title").split(" - ")[0];
            if (currentDate.equalsIgnoreCase(redditDate)) {
                String[] events = recent.getString("selftext").split("\\n\\n");
                Log.i("APIs.Reddit", "Updating events from /u/torontothingstodo");
                for (String event : events) {
                    String title = event.replaceAll("(.*\\[)|(\\].*)", "");
                    String link = event.replaceAll("(.*\\()|(\\).*)", "");
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
}
