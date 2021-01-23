package com.test.vocaup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MyWordListSinglyFragment extends Fragment {

    private ImageView image_word;
    private Button btn_pron;
    private Button btn_back;
    private Button btn_next;
    private TextView text_mean;
    private TextView text_s_mean;
    private TextView text_sentence;

    public static MyWordListSinglyFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new MyWordListSinglyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mywordlistsingly, container, false);
    }
}
