package com.test.vocaup.server;

import android.os.AsyncTask;
import android.util.Log;

import com.test.vocaup.DO.ListAll;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Connect_get {
    private String url = "http://192.168.0.107:5000/";
    private ArrayList<ListAll> result = new ArrayList<>();

    public ArrayList get(String... strings) {
        try {
            OkHttpClient httpClient = new OkHttpClient();
            //RequestBody body = null;
            Request request = null;

            request = new Request.Builder().url(url + strings[0] + "/" + strings[1]).build();

            System.out.println(url + strings[0] + "/" + strings[1]);

            Response response = httpClient.newCall(request).execute();

            String jsonData = response.body().string();
            JSONObject object = new JSONObject(jsonData);
            JSONArray array = object.getJSONArray("result");

            for(int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                ListAll listAll = new ListAll();
                listAll.setWord(jsonObject.getString("word"));
                listAll.setMean(jsonObject.getString("mean_w"));
                listAll.setPart(jsonObject.getString("part"));
                listAll.setLevel(jsonObject.getString("level"));
                result.add(listAll);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
