package com.test.vocaup.server;

import android.os.AsyncTask;
import android.util.Log;

import com.bumptech.glide.load.PreferredColorSpace;
import com.google.android.gms.common.util.Strings;
import com.test.vocaup.DO.ListAll;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Connect_get implements Interceptor {
    //private String url = "http://10.0.2.2:5000/";
    private String url = "http://13.209.75.148:5000/";
    private ArrayList<ListAll> result = new ArrayList<>();

    private String userToken = "";

    public Connect_get(String userToken) {
        this.userToken = userToken;
    }

    public Connect_get() {
    }

    public ArrayList get(String... strings) {
        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(this::intercept)
                .build();
        Request request = null;
        request = new Request.Builder().url(url + strings[0] + "/" + strings[1]).build();

        if(strings[0].equals("list")) {
            try {
                Response response = httpClient.newCall(request).execute();

                String jsonData = response.body().string();
                JSONObject object = new JSONObject(jsonData);
                JSONArray array = object.getJSONArray("result");
                //System.out.println(jsonData);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    ListAll listAll = new ListAll();
                    listAll.setIndex(jsonObject.getInt("index"));
                    listAll.setWord(jsonObject.getString("word").replaceAll("[0-9]", ""));
                    listAll.setOrigin_word(jsonObject.getString("word"));
                    listAll.setMean(jsonObject.getString("mean_w"));
                    listAll.setPart(jsonObject.getString("part"));
                    listAll.setLevel(jsonObject.getString("level"));
                    listAll.setSentence(jsonObject.getString("sentence"));
                    listAll.setMean_s(jsonObject.getString("mean_s"));
                    listAll.setUserWord(jsonObject.getInt("userWord"));
                    if(listAll.getUserWord() == 1) {
                        listAll.setSelected(true);
                    } else {
                        listAll.setSelected(false);
                    }

                    result.add(listAll);
                }

                response.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        return null;
    }

    public ArrayList UserWordget(String key) {
        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(this::intercept)
                .build();
        Request request = null;
        request = new Request.Builder().url(url + key).build();

        if(key.equals("userWordList")) {
            try {
                Response response = httpClient.newCall(request).execute();

                String jsonData = response.body().string();
                JSONObject object = new JSONObject(jsonData);
                JSONArray array = object.getJSONArray("result");
                System.out.println(jsonData);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    ListAll listAll = new ListAll();
                    listAll.setIndex(jsonObject.getInt("index"));
                    listAll.setWord(jsonObject.getString("word").replaceAll("[0-9]", ""));
                    listAll.setOrigin_word(jsonObject.getString("word"));
                    listAll.setMean(jsonObject.getString("mean_w"));
                    listAll.setPart(jsonObject.getString("part"));
                    listAll.setLevel(jsonObject.getString("level"));
                    listAll.setSentence(jsonObject.getString("sentence"));
                    listAll.setMean_s(jsonObject.getString("mean_s"));
                    listAll.setUserWord(jsonObject.getInt("userWord"));
                    if(listAll.getUserWord() == 1) {
                        listAll.setSelected(true);
                    } else {
                        listAll.setSelected(false);
                    }

                    result.add(listAll);
                }

                response.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        return null;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .addHeader("UserToken", userToken)
                .build();
        Response response = chain.proceed(newRequest);
        return response;
    }

    public JSONObject problem_get(String... strings) {
        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(this::intercept)
                .build();

        Request request = null;
        JSONObject result = null;
        request = new Request.Builder().url(url + strings[0] + "/" + strings[1]).build();

        try {
            Response response = httpClient.newCall(request).execute();

            String jsonData = response.body().string();
            JSONObject object = new JSONObject(jsonData);
            System.out.println(object);
            result = object;

            response.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
