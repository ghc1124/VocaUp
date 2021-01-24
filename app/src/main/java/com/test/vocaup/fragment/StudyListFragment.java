package com.test.vocaup.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.test.vocaup.DO.ListAll;
import com.test.vocaup.R;
import com.test.vocaup.activity.MenuActivity;
import com.test.vocaup.server.Connect_get;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StudyListFragment extends Fragment {
    private Button btn_act;
    private Button btn_once;

    private String url = "http://192.168.0.107:5000/";
    private ArrayList<ListAll> result = new ArrayList<>();

    public static StudyListFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new StudyListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_studylist, container, false);

        btn_act = viewGroup.findViewById(R.id.btn_act);
        btn_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MenuActivity) getActivity()).replaceFragment(StudyFragment.newInstance());
            }
        });

        btn_once = viewGroup.findViewById(R.id.btn_once);
        btn_once.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    OkHttpClient httpClient = new OkHttpClient();
                    //RequestBody body = null;
                    Request request = null;

                    request = new Request.Builder().url(url + "list/" + "1").build();

                    httpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.d("GET", "\"list\" request fail!!");
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            try {
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

                                System.out.println(result.size());
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return viewGroup;
    }
}