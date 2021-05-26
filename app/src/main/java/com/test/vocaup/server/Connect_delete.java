package com.test.vocaup.server;

import com.test.vocaup.DO.Manager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Connect_delete implements Interceptor {
    private String url = "http://10.0.2.2:5000/";
    //private String url = "http://13.209.75.148:5000/";

    private String userToken = "";

    public Connect_delete(String userToken) {
        this.userToken = userToken;
    }

    public void removeUserWord(int index) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(this::intercept)
                .retryOnConnectionFailure(true)
                .build();
        Request request = null;
        Response response = null;

        request = new Request.Builder().url(url + "/userWord/" + index).delete().build();

        try {
            response = httpClient.newCall(request).execute();
        } catch(Exception e) {
            e.printStackTrace();
        }

        response.close();
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
}
