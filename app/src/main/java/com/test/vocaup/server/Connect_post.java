package com.test.vocaup.server;

import com.test.vocaup.DO.Manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Connect_post {
    //private String url = "http://10.0.2.2:5000/";
    private String url = "http://13.209.75.148:5000/";

    public Manager getUserInfo(Manager manager) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                object.toString()
        );

        request = new Request.Builder().url(url + "user").post(body).build();

        try {
            response = httpClient.newCall(request).execute();

//            Thread.sleep(100);

            if(response != null) {
                data = response.body().string();
                jsonData = new JSONObject(data);

                System.out.println(jsonData);

                if(jsonData.getString("Token").equals(result.getToken())) {
                    result.setLevel(jsonData.getInt("Level"));
                    if(jsonData.getString("UserWord") != null)
                        result.setUserWord((String)jsonData.getString("UserWord"));
                    if(jsonData.getString("ProblemSet") != null)
                        result.setProblemSet((String)jsonData.getString("ProblemSet"));
                    result.setBlank_spelling(jsonData.getInt("blank_spelling"));
                    result.setMean_spelling(jsonData.getInt("mean_spelling"));
                    result.setSpelling_mean(jsonData.getInt("spelling_mean"));
                    result.setSpelling_mean_link(jsonData.getInt("spelling_mean_link"));
                    result.setSpelling_sort(jsonData.getInt("spelling_sort"));
                    result.setPron_mean(jsonData.getInt("pron_mean"));
                    result.setCross_puz(jsonData.getInt("cross_puz"));
                    result.setRecap(jsonData.getInt("recap"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.close();

        return result;
    }
}
