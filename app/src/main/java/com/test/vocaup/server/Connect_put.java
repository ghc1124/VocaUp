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
    //private String url = "http://10.0.2.2:5000/";
    private String url = "http://13.209.75.148:5000/";

    private String userToken = "";

    public Connect_put(String userToken) {
        this.userToken = userToken;
    }

    public Connect_put() {
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

            if (manager.getPron_mean() != -1) {
                object.put("pron_mean", manager.getPron_mean());
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.close();

        return result;
    }

    public void appendWrongList(ArrayList<String> strings, int level) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(this::intercept)
                .retryOnConnectionFailure(true)
                .build();
        RequestBody body = null;
        Request request = null;
        Response response = null;

        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();

        for (String string : strings) {
            array.put(string);
        }

        try {
            object.put("wrongList", array);
        } catch(Exception e) {
            e.printStackTrace();
        }

        body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                object.toString()
        );

        System.out.println(body);

        request = new Request.Builder().url(url + "/wrongList/" + level).put(body).build();

        try {
            response = httpClient.newCall(request).execute();
        } catch(Exception e) {
            e.printStackTrace();
        }

        response.close();
    }

    public void appendUserWord(int index) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(this::intercept)
                .retryOnConnectionFailure(true)
                .build();
        RequestBody body = null;
        Request request = null;
        Response response = null;
        String data = "";

        body = RequestBody.create(null, "");

        request = new Request.Builder().url(url + "/userWordA/" + index).put(body).build();

        try {
            response = httpClient.newCall(request).execute();
        } catch(Exception e) {
            e.printStackTrace();
        }

        response.close();
    }

    public void removeUserWord(int index) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(this::intercept)
                .retryOnConnectionFailure(true)
                .build();
        RequestBody body = null;
        Request request = null;
        Response response = null;
        String data = "";

        body = RequestBody.create(null, "");

        request = new Request.Builder().url(url + "/userWordR/" + index).put(body).build();

        try {
            response = httpClient.newCall(request).execute();
        } catch(Exception e) {
            e.printStackTrace();
        }

        response.close();
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
