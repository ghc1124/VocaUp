package com.test.vocaup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyWordListFragment extends Fragment {

    public static MyWordListFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new MyWordListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mywordlist, container, false);
    }
}