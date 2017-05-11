package com.UTS.locaTO.APIs;

import android.os.AsyncTask;
import android.util.Log;

import com.UTS.locaTO.MainActivity;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by marcel on 5/11/17.
 */

public class Reddit extends AsyncTask<Void, Void, String> {

    private MainActivity mActivity;
    private OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public Reddit(MainActivity activity) {
        this.mActivity = activity;
    }

    protected String doInBackground(Void... voids) {

        Request request = new Request.Builder()
                .url("https://www.reddit.com/user/torontothingstodo/submitted.json")
                .build();
        try {
            Response response = this.client.newCall(request).execute();
            Log.i("Reddit: ", response.body().string());
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
