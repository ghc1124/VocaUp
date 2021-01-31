package com.test.vocaup.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.test.vocaup.R;
import com.test.vocaup.activity.MenuActivity;

public class ExamReadyFragment extends Fragment {
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

}
