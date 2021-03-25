package com.test.vocaup.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.test.vocaup.DO.Manager;
import com.test.vocaup.R;
import com.test.vocaup.activity.MainActivity;
import com.test.vocaup.activity.MenuActivity;
import com.test.vocaup.server.Connect_post;
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

    public static MenuFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new MenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_menu, container, false);

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
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        // 사용자 정보 등록 or 받아오기
                        Connect_put connect_put = new Connect_put();
                        Manager testManager = new Manager(((MenuActivity)getActivity()).token);
                        testManager.setLevel(1);

//                        testManager.setRecap(5);
//                        testManager.setBlank_spelling(0);
//                        testManager.setMean_spelling(0);
//                        testManager.setSpelling_mean(0);
//                        testManager.setSpelling_mean_link(0);
//                        testManager.setSpelling_sort(0);

                        testManager.setRecap(5);
                        testManager.setBlank_spelling(1);
                        testManager.setMean_spelling(1);
                        testManager.setSpelling_mean(1);
                        testManager.setSpelling_mean_link(1);
                        testManager.setSpelling_sort(0);
                        testManager.setPron_mean(0);

                        Manager resultManager = connect_put.changeUserInfo(testManager);
                        ((MenuActivity)getActivity()).manager = ((MenuActivity)getActivity()).getManager();
                    }
                };

                thread.start();

                try {
                    thread.join();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return viewGroup;
    }
}