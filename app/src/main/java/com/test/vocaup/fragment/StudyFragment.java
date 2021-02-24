package com.test.vocaup.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.test.vocaup.R;
import com.test.vocaup.quiz.BlankSpelling;
import com.test.vocaup.quiz.MeanSpelling;
import com.test.vocaup.quiz.SpellingMean;
import com.test.vocaup.activity.MenuActivity;

public class StudyFragment extends Fragment implements MenuActivity.OnBackPressedListener{

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
        StudyBtnOnClickListener but_listener = new StudyBtnOnClickListener();
        btn_w_to_m = viewGroup.findViewById(R.id.btn_w_to_m);
        btn_m_to_w = viewGroup.findViewById(R.id.btn_m_to_w);
        btn_fill_blank = viewGroup.findViewById(R.id.btn_fill_blank);
        btn_w_to_m.setOnClickListener(but_listener);
        btn_m_to_w.setOnClickListener(but_listener);
        btn_fill_blank.setOnClickListener(but_listener);
        return viewGroup;
    }

    class StudyBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), SpellingMean.class);  //기본창
            switch (view.getId()) {
                case R.id.btn_w_to_m:
                    intent = new Intent(getActivity(), SpellingMean.class);
                    break;
                case R.id.btn_m_to_w:
                    intent = new Intent(getActivity(), MeanSpelling.class);
                    break;
                case R.id.btn_fill_blank:
                    intent = new Intent(getActivity(), BlankSpelling.class);
                    break;
                case R.id.button3:

                    break;
            }
            intent.putExtra("Token", ((MenuActivity) getActivity()).manager.getToken());
            startActivity(intent);
        }


    }
    @Override
    public void onBack() {
        Log.e("Other", "onBack()");
        // 리스너를 설정하기 위해 Activity 를 받아옴
        MenuActivity activity = (MenuActivity)getActivity();
        // 한번 뒤로가기 버튼을 눌렀다면 Listener 를 null로 설정
        activity.setOnBackPressedListener(null);
        // MainFragment 로 교체
        ((MenuActivity) getActivity()).replaceFragment(StudyListFragment.newInstance());
    }

    // Fragment 호출 시 반드시 호출되는 오버라이드 메소드
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Other", "onAttach()");
        ((MenuActivity)context).setOnBackPressedListener(this);
    }
}
