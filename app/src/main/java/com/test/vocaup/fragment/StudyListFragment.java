package com.test.vocaup.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;

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
    private Button button;

    private Dialog number;
    private NumberPicker numberPicker;

    public static StudyListFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new StudyListFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_studylist, container, false);

        number = new Dialog(getActivity());
        number.requestWindowFeature(Window.FEATURE_NO_TITLE);
        number.setContentView(R.layout.dialog_number);

        button = number.findViewById(R.id.button_ok);

        numberPicker = number.findViewById(R.id.numberPicker);

        numberPicker.setWrapSelectorWheel(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            numberPicker.setTextColor(Color.BLACK);
        }

        numberPicker.setMaxValue(35);
        numberPicker.setMinValue(1);
        numberPicker.setValue(((MenuActivity) getActivity()).manager.getLevel());

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
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MenuActivity)MenuActivity.context).level = numberPicker.getValue();
                        ((MenuActivity) getActivity()).replaceFragment(AllWordFragment.newInstance());

                        number.dismiss();
                    }
                });

                number.create();
                number.show();
            }
        });

        btn_singly = viewGroup.findViewById(R.id.btn_singly);
        btn_singly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MenuActivity)MenuActivity.context).level = numberPicker.getValue();
                        ((MenuActivity) getActivity()).replaceFragment(AllWordSinglyFragment.newInstance());

                        number.dismiss();
                    }
                });

                number.create();
                number.show();
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