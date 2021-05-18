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

import com.test.vocaup.DO.Manager;
import com.test.vocaup.R;
import com.test.vocaup.activity.MenuActivity;
import com.test.vocaup.quiz.BlankSpelling;
import com.test.vocaup.quiz.CrossWord;
import com.test.vocaup.quiz.MeanSpelling;
import com.test.vocaup.quiz.PronMean;
import com.test.vocaup.quiz.SpellingMean;
import com.test.vocaup.quiz.SpellingMeanLink;
import com.test.vocaup.quiz.SpellingSort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ExamFragment extends Fragment implements MenuActivity.OnBackPressedListener{
    private TextView text_level;
    private Button btn_w_to_m;
    private Button btn_m_to_w;
    private Button btn_match;
    private Button btn_p_to_m;
    private Button btn_sort;
    private Button btn_fill_blank;
    private Button btn_back;
    private Button btn_cross;
    private Button btn_recap;

    private Manager current_manager;

    public static ExamFragment newInstance() {
        return new ExamFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_exam, container, false);
        StudyBtnOnClickListener but_listener = new StudyBtnOnClickListener();
        btn_w_to_m = viewGroup.findViewById(R.id.btn_w_to_m);
        btn_m_to_w = viewGroup.findViewById(R.id.btn_m_to_w);
        btn_fill_blank = viewGroup.findViewById(R.id.btn_fill_blank);
        btn_match = viewGroup.findViewById(R.id.btn_match);
        btn_sort = viewGroup.findViewById(R.id.btn_sort);
        btn_p_to_m = viewGroup.findViewById(R.id.btn_p_to_m);
        btn_cross = viewGroup.findViewById(R.id.btn_cross);
        btn_recap = viewGroup.findViewById(R.id.btn_recap);

        btn_back = viewGroup.findViewById(R.id.btn_back);

        btn_w_to_m.setOnClickListener(but_listener);
        btn_m_to_w.setOnClickListener(but_listener);
        btn_fill_blank.setOnClickListener(but_listener);
        btn_match.setOnClickListener(but_listener);
        btn_sort.setOnClickListener(but_listener);
        btn_p_to_m.setOnClickListener(but_listener);
        btn_cross.setOnClickListener(but_listener);

        btn_recap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Class> strings = new ArrayList<>();
                int CurrentLevel = ((MenuActivity) getActivity()).manager.getLevel();
                int LevelInfo = 0;
                Random random = new Random();

                strings.add(SpellingMean.class);
                strings.add(MeanSpelling.class);
                strings.add(BlankSpelling.class);
                strings.add(SpellingMeanLink.class);
                strings.add(SpellingSort.class);
                strings.add(PronMean.class);
                strings.add(CrossWord.class);

                Collections.shuffle(strings);
                if (CurrentLevel == 1) {
                    LevelInfo = 1;
                } else if (CurrentLevel < 4) {
                    LevelInfo = 1 + random.nextInt(CurrentLevel);
                } else {
                    LevelInfo = (CurrentLevel - 3) + random.nextInt(4);
                }

                Intent intent = new Intent(getActivity(), strings.get(0));
                intent.putExtra("Token", ((MenuActivity) getActivity()).manager.getToken());
                intent.putExtra("levelInfo", LevelInfo);
                intent.putExtra("ExamFlag", true);
                intent.putExtra("RecapFlag", true);
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MenuActivity) getActivity()).replaceFragment(MenuFragment.newInstance());
            }
        });

        text_level = viewGroup.findViewById(R.id.text_level);

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
                case R.id.btn_match:
                    intent = new Intent(getActivity(), SpellingMeanLink.class);
                    break;
                case R.id.btn_sort:
                    intent = new Intent(getActivity(), SpellingSort.class);
                    break;
                case R.id.btn_p_to_m:
                    intent = new Intent(getActivity(), PronMean.class);
                    break;
                case R.id.btn_cross:
                    intent = new Intent(getActivity(), CrossWord.class);
                    break;
            }

            intent.putExtra("Token", ((MenuActivity) getActivity()).manager.getToken());
            intent.putExtra("levelInfo", ((MenuActivity) getActivity()).manager.getLevel());
            intent.putExtra("ExamFlag", true);
            intent.putExtra("RecapFlag", false);
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
        ((MenuActivity) getActivity()).replaceFragment(ExamReadyFragment.newInstance());
    }

    // Fragment 호출 시 반드시 호출되는 오버라이드 메소드
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Other", "onAttach()");
        ((MenuActivity)context).setOnBackPressedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        current_manager = ((MenuActivity) getActivity()).manager;

        text_level.setText("현재 VocaUp lv. " + current_manager.getLevel() +
                "\n승급 후 VocaUp lv. " + (current_manager.getLevel() + 1));

        if(current_manager.getBlank_spelling() != 0) {
            btn_fill_blank.setEnabled(false);
            btn_fill_blank.setBackgroundResource(R.drawable.button_blue_big4);
        } else {
            btn_fill_blank.setEnabled(true);
            btn_fill_blank.setBackgroundResource(R.drawable.button_blue_big3);
        }

        if(current_manager.getMean_spelling() != 0) {
            btn_m_to_w.setEnabled(false);
            btn_m_to_w.setBackgroundResource(R.drawable.button_blue_big4);
        } else {
            btn_m_to_w.setEnabled(true);
            btn_m_to_w.setBackgroundResource(R.drawable.button_blue_big3);
        }

        if(current_manager.getSpelling_mean() != 0) {
            btn_w_to_m.setEnabled(false);
            btn_w_to_m.setBackgroundResource(R.drawable.button_blue_big4);
        } else {
            btn_w_to_m.setEnabled(true);
            btn_w_to_m.setBackgroundResource(R.drawable.button_blue_big3);
        }

        if(current_manager.getSpelling_mean_link() != 0) {
            btn_match.setEnabled(false);
            btn_match.setBackgroundResource(R.drawable.button_blue_big4);
        } else {
            btn_match.setEnabled(true);
            btn_match.setBackgroundResource(R.drawable.button_blue_big3);
        }

        if(current_manager.getSpelling_sort() != 0) {
            btn_sort.setEnabled(false);
            btn_sort.setBackgroundResource(R.drawable.button_blue_big4);
        } else {
            btn_sort.setEnabled(true);
            btn_sort.setBackgroundResource(R.drawable.button_blue_big3);
        }

        if(current_manager.getPron_mean() != 0) {
            btn_p_to_m.setEnabled(false);
            btn_p_to_m.setBackgroundResource(R.drawable.button_blue_big4);
        } else {
            btn_p_to_m.setEnabled(true);
            btn_p_to_m.setBackgroundResource(R.drawable.button_blue_big3);
        }

        if(current_manager.getCross_puz() != 0) {
            btn_cross.setEnabled(false);
            btn_cross.setBackgroundResource(R.drawable.button_blue_big4);
        } else {
            btn_cross.setEnabled(true);
            btn_cross.setBackgroundResource(R.drawable.button_blue_big3);
        }

        if(current_manager.getRecap() == 5) {
            btn_recap.setEnabled(false);
            btn_recap.setBackgroundResource(R.drawable.button_blue_big4);
        } else {
            btn_recap.setEnabled(true);
            btn_recap.setBackgroundResource(R.drawable.button_blue_big3);
        }
    }
}
