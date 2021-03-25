package com.test.vocaup.server;

import com.test.vocaup.DO.ListAll;
import com.test.vocaup.DO.Manager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Connect_put implements Interceptor {
    //private String url = "http://172.17.9.105:5000/";
    private String url = "http://13.209.75.148:5000/";

    private String userToken = "";

    public Connect_put(String userToken) {
        this.userToken = userToken;
    }

    public Connect_put() {
    }

    public void updateSet() {
        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(this::intercept)
                .build();
        RequestBody body = new FormBody
                .Builder()
                .add("UserToken", userToken)
                .build();
        Request request = null;

        request = new Request.Builder().url(url + "problemSet").put(body).build();

        try {
            Response response = httpClient.newCall(request).execute();

            String jsonData = response.body().string();

            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Manager changeUserInfo(Manager manager) {
        Manager result = new Manager(manager.getToken());

        JSONObject object = new JSONObject();
        JSONObject jsonData = null;

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        RequestBody body = null;
        Request request = null;
        Response response = null;
        String data = "";

        try {
            if (manager.getToken() != null) {
                object.put("Token", manager.getToken());
            }

            if (manager.getLevel() != 0) {
                object.put("Level", manager.getLevel());
            }

            if (manager.getUserWord() != null) {
                object.put("UserWord", manager.getUserWord());
            }

            if (manager.getProblemSet() != null) {
                object.put("ProblemSet", manager.getProblemSet());
            }

            if (manager.getBlank_spelling() != -1) {
                object.put("blank_spelling", manager.getBlank_spelling());
            }

            if (manager.getMean_spelling() != -1) {
                object.put("mean_spelling", manager.getMean_spelling());
            }

            if (manager.getSpelling_mean() != -1) {
                object.put("spelling_mean", manager.getSpelling_mean());
            }

            if (manager.getSpelling_mean_link() != -1) {
                object.put("spelling_mean_link", manager.getSpelling_mean_link());
            }

            if (manager.getSpelling_sort() != -1) {
                object.put("spelling_sort", manager.getSpelling_sort());
            }

            if (manager.getRecap() != -1) {
                object.put("recap", manager.getRecap());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                object.toString()
        );

        request = new Request.Builder().url(url + "/user").put(body).build();

//        System.out.println("request 완료");

        try {
            response = httpClient.newCall(request).execute();

//            Thread.sleep(100);

//            if(response != null) {
//                data = response.body().string();
//                jsonData = new JSONObject(data);
//
//                if(jsonData.getString("Token").equals(result.getToken())) {
//                    result.setLevel(jsonData.getInt("Level"));
//                    if(jsonData.getString("UserWord") != null)
//                        result.setUserWord((String)jsonData.getString("UserWord"));
//                    if(jsonData.getString("ProblemSet") != null)
//                        result.setProblemSet((String)jsonData.getString("ProblemSet"));
//                    result.setBlank_spelling(jsonData.getInt("blank_spelling"));
//                    result.setMean_spelling(jsonData.getInt("mean_spelling"));
//                    result.setSpelling_mean(jsonData.getInt("spelling_mean"));
//                    result.setSpelling_mean_link(jsonData.getInt("spelling_mean_link"));
//                    result.setSpelling_sort(jsonData.getInt("spelling_sort"));
//                    result.setRecap(jsonData.getInt("recap"));
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.close();

        return result;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .addHeader("UserToken", userToken)
                .build();
        Response response = chain.proceed(newRequest);
        return response;
    }
}
