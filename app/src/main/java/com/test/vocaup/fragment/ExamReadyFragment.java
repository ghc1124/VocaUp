package com.test.vocaup.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.test.vocaup.R;
import com.test.vocaup.activity.MenuActivity;

public class ExamReadyFragment extends Fragment implements MenuActivity.OnBackPressedListener {
    public static ExamReadyFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new ExamReadyFragment();
    }

    private Button btn_start; // 시험 시작 버튼

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_examready, container, false);


        btn_start = viewGroup.findViewById(R.id.btn_start); // 시험 시작 버튼 할당
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MenuActivity) getActivity()).replaceFragment(ExamFragment.newInstance());
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
        // Activity 에서도 뭔가 처리하고 싶은 내용이 있다면 하단 문장처럼 호출
    }

    // Fragment 호출 시 반드시 호출되는 오버라이드 메소드
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Other", "onAttach()");
        ((MenuActivity)context).setOnBackPressedListener(this);
    }

}
