package com.test.vocaup.server;

import android.os.AsyncTask;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class connect_get {
    private String url = "http://192.168.0.107:5000/";

    public void get(String... strings) {
        try {
            OkHttpClient httpClient = new OkHttpClient();
            //RequestBody body = null;
            Request request = null;

            request = new Request.Builder().url(url + strings[0]).build();

            System.out.println(url + strings[0]);

            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("GET", "\"list\" request fail!!");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String jsonData = response.body().string();
                    try {
                        JSONObject object = new JSONObject(jsonData);
                        JSONArray array = object.getJSONArray("result");
                        System.out.println(array);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
