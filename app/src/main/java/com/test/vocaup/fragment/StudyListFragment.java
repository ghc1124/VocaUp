package com.test.vocaup.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.test.vocaup.R;
import com.test.vocaup.activity.MenuActivity;
import com.test.vocaup.server.connect_get;

public class StudyListFragment extends Fragment {

    private Button btn_act;
    private Button btn_once;

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
                new connect_get().get("list");
            }
        });

        return viewGroup;
    }
}