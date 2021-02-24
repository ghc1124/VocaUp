package com.test.vocaup.fragment;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.test.vocaup.R;
import com.test.vocaup.activity.MainActivity;
import com.test.vocaup.activity.MenuActivity;

import java.io.IOException;

public class MyWordListSinglyFragment extends Fragment implements MenuActivity.OnBackPressedListener{

    private ImageView image_word;
    private Button btn_pron;
    private Button btn_back;
    private Button btn_next;
    private TextView text_mean;
    private TextView text_s_mean;
    private TextView text_sentence;
    MediaPlayer mediaPlayer;

    private String[] word = {"apple","banana","grape","orange"};
    private String[] korea = {"사과","바나나","포도","오렌지"};
    private int[] word_index = {1,2,3,4};
    private int now_index = 0;
    private int last_index = word_index.length;

    public static MyWordListSinglyFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new MyWordListSinglyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_mywordlistsingly, container, false);


        text_mean = viewGroup.findViewById(R.id.text_mean);
        text_mean.setText(korea[0]);

        btn_pron = viewGroup.findViewById(R.id.btn_pron); // 발음 듣기 버튼 할당
        btn_pron.setText(word[0]);
        btn_pron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(getContext(), R.raw.success); //발음 파일 이름 변경하면 됨
                mediaPlayer.start();
            }
        });

        btn_back = viewGroup.findViewById(R.id.btn_back); // 뒤로 버튼 할당
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(now_index==0){
                    Toast myToast = Toast.makeText(getContext(),"이전 단어가 없습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else {
                    now_index--;
                    btn_pron.setText(word[now_index]); // 증가 시킨 인덱스의 단어로 텍스트 변경
                    text_mean.setText(korea[now_index]);
                }
            }
        });

        btn_next = viewGroup.findViewById(R.id.btn_next); // 다음 버튼 할당
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(now_index==last_index-1){
                    Toast myToast = Toast.makeText(getContext(),"다음 단어가 없습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else {
                    now_index++;
                    btn_pron.setText(word[now_index]); // 증가 시킨 인덱스의 단어로 텍스트 변경
                    text_mean.setText(korea[now_index]);
                }
            }
        });

        return viewGroup;
    }

    @Override
    public void onBack() {
        Log.e("Other", "onBack()");
        // 리스너를 설정하기 위해 Activity 를 받아옴
        MenuActivity activity = (MenuActivity)getActivity();
        // 한번 뒤로가기 버튼을 눌렀다면 Listener 를 null로 설정
        activity.setOnBackPressedListener(null);
        // MainFragment 로 교체
        ((MenuActivity) getActivity()).replaceFragment(MyWordListFragment.newInstance());
    }

    // Fragment 호출 시 반드시 호출되는 오버라이드 메소드
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Other", "onAttach()");
        ((MenuActivity)context).setOnBackPressedListener(this);
    }
}
