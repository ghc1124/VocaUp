package com.test.vocaup.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.test.vocaup.R;

public class MyWordListOnceFragment extends Fragment {
    public static MyWordListOnceFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new MyWordListOnceFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mywordlistonce, container, false);
    }
}
