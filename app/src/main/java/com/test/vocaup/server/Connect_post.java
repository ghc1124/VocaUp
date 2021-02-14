package com.test.vocaup.server;

import com.test.vocaup.DO.ListAll;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Connect_post implements Interceptor {
    private String url = "http://192.168.0.107:5000/";
    //private String url = "http://13.209.75.148:5000/";

    private String userToken = "";

    public Connect_post(String userToken) {
        this.userToken = userToken;
    }

    public void updateSet() {
        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(this::intercept)
                .build();
        //RequestBody body = null;
        Request request = null;

        //request = new Request.Builder().url(url + "problemSet").post().build();

        try {
            Response response = httpClient.newCall(request).execute();

            String jsonData = response.body().string();

            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
