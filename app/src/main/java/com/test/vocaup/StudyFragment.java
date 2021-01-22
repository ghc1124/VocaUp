package com.test.vocaup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class StudyFragment extends Fragment {
    public static StudyFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new StudyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_study, container, false);
    }
}
