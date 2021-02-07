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
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Connect_get implements Interceptor {
    //private String url = "http://192.168.0.107:5000/";
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
        //RequestBody body = null;
        Request request = null;

        request = new Request.Builder().url(url + strings[0] + "/" + strings[1]).build();

//      System.out.println(url + strings[0] + "/" + strings[1]);
//      전체 리스트 요청할 때는 /list/{"레벨"}

        if(strings[0].equals("list")) {
            try {
                Response response = httpClient.newCall(request).execute();

                String jsonData = response.body().string();
                JSONObject object = new JSONObject(jsonData);
                JSONArray array = object.getJSONArray("result");
                //System.out.println(array);

                for(int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    ListAll listAll = new ListAll();
                    listAll.setWord(jsonObject.getString("word").replaceAll("[0-9]", ""));
                    listAll.setMean(jsonObject.getString("mean_w"));
                    listAll.setPart(jsonObject.getString("part"));
                    listAll.setLevel(jsonObject.getString("level"));
                    result.add(listAll);
                }

            } catch(Exception e) {
                e.printStackTrace();
            }

            return result;
        } else {
            try {
                Response response = httpClient.newCall(request).execute();

                String jsonData = response.body().string();
                JSONObject object = new JSONObject(jsonData);
                System.out.println(object);

                /*for(int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);

                }*/

            } catch(Exception e) {
                e.printStackTrace();
            }
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

    public JSONObject problem_get(String... strings){
        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(this::intercept)
                .build();

        Request request = null;
        JSONObject result=null;
        request = new Request.Builder().url(url + strings[0] + "/" + strings[1]).build();

        try {
            Response response = httpClient.newCall(request).execute();

            String jsonData = response.body().string();
            JSONObject object = new JSONObject(jsonData);
            System.out.println(object);
            result=object;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
