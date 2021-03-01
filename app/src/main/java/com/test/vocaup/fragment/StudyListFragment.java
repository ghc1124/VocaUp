package com.test.vocaup.fragment;

import android.content.Context;
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

public class StudyListFragment extends Fragment implements MenuActivity.OnBackPressedListener {
    private Button btn_act;
    private Button btn_once;
    private Button btn_singly;

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
                ((MenuActivity) getActivity()).replaceFragment(AllWordFragment.newInstance());
            }
        });

        btn_singly = viewGroup.findViewById(R.id.btn_singly);
        btn_singly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MenuActivity) getActivity()).replaceFragment(AllWordSinglyFragment.newInstance());
            }
        });

        return viewGroup;
    }

    @Override
    public void onBack() {
        Log.e("Other", "onBack()");
        // 리스너를 설정하기 위해 Activity 를 받아옴
        MenuActivity activity = (MenuActivity)getActivity();
        // 한번 뒤로가기 버튼을 눌렀다면 Listener 를 null로 설정
        activity.setOnBackPressedListener(null);
        // MainFragment 로 교체
        ((MenuActivity) getActivity()).replaceFragment(MenuFragment.newInstance());
    }

    // Fragment 호출 시 반드시 호출되는 오버라이드 메소드
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Other", "onAttach()");
        ((MenuActivity)context).setOnBackPressedListener(this);
    }
}