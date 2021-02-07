package com.test.vocaup.fragment;

import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.test.vocaup.R;
import com.test.vocaup.activity.SpellingMean;
import com.test.vocaup.activity.MenuActivity;

public class StudyFragment extends Fragment {

    private TextView text_level;
    private Button btn_w_to_m;
    private Button btn_m_to_w;
    private Button btn_match;
    private Button btn_p_to_m;
    private Button btn_arrange;
    private Button btn_fill_blank;
    private Button btn_back;

    public static StudyFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new StudyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_study, container, false);
        btn_w_to_m = viewGroup.findViewById(R.id.btn_w_to_m);
        btn_w_to_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SpellingMean.class);
                // 추가 사항
                intent.putExtra("Token", ((MenuActivity)getActivity()).manager.getToken());
                startActivity(intent);
            }
        });

        return viewGroup;
    }


}
