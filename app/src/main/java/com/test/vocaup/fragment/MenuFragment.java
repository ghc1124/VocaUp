package com.test.vocaup.fragment;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.test.vocaup.DO.Manager;
import com.test.vocaup.R;
import com.test.vocaup.activity.MenuActivity;
import com.test.vocaup.quiz.CrossWord;
import com.test.vocaup.server.Connect_put;

import java.util.Stack;

public class MenuFragment extends Fragment  {
    public static FragmentManager manager;
    public static Stack<Fragment> fragmentStack;
    public static FragmentTransaction transaction;

    private LottieAnimationView imgBtn_myWord; // 나만의 단어장 이미지 버튼
    private LottieAnimationView imgBtn_exam; // 시험 이미지 버튼
    private LottieAnimationView imgBtn_study; // 학습 이미지 버튼
    private ImageButton imgBtn_lab;

    private MediaPlayer mediaPlayer;

    private ImageView imageView_test;

    public static MenuFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new MenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_menu, container, false);

        imageView_test = viewGroup.findViewById(R.id.imageView_test);

        imgBtn_myWord = viewGroup.findViewById(R.id.imgBtn_myWord); // 나만의 단어장 버튼 할당
        imgBtn_myWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MenuActivity) getActivity()).replaceFragment(MyWordListFragment.newInstance());
            }
        });

        imgBtn_exam = viewGroup.findViewById(R.id.imgBtn_exam); // 시험 버튼 할당
        imgBtn_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MenuActivity) getActivity()).replaceFragment(ExamReadyFragment.newInstance());
            }
        });

        imgBtn_study = viewGroup.findViewById(R.id.imgBtn_study); // 학습 버튼 할당
        imgBtn_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MenuActivity) getActivity()).replaceFragment(StudyListFragment.newInstance());
            }
        });

        imgBtn_lab = viewGroup.findViewById(R.id.imgBtn_lab);
        imgBtn_lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "더 멋진 기능을 준비중입니다!!", Toast.LENGTH_SHORT).show();
            }
        });

        return viewGroup;
    }
}