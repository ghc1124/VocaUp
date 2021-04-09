package com.test.vocaup.fragment;

import android.content.Context;
import android.media.MediaPlayer;
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

import com.bumptech.glide.Glide;
import com.test.vocaup.DO.ListAll;
import com.test.vocaup.R;
import com.test.vocaup.activity.MenuActivity;
import com.test.vocaup.server.Connect_get;

import java.util.ArrayList;

public class AllWordSinglyFragment extends Fragment implements MenuActivity.OnBackPressedListener{
    private ArrayList<ListAll> result = new ArrayList<>();

    private ImageView image_word;
    private Button btn_pron;
    private Button btn_back;
    private Button btn_next;
    private TextView text_mean;
    private TextView text_part;
    private TextView text_mean_s;
    private TextView text_sentence;
    MediaPlayer mediaPlayer;

    /*private String[] word = {"apple","banana","grape","orange"};
    private String[] korea = {"사과","바나나","포도","오렌지"};
    private int[] word_index = {1,2,3,4}; */
    private int now_index = 0;
    private int last_index = 0;

    public static AllWordSinglyFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new AllWordSinglyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_mywordlistsingly, container, false);

        Thread thread = new Thread() {
            @Override
            public void run() {
                result = new Connect_get().get("list", "1");
            }
        };

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        last_index = result.size() - 1;

        text_mean = viewGroup.findViewById(R.id.text_mean);
        text_mean.setText(result.get(0).getMean());

        text_part = viewGroup.findViewById(R.id.text_part);
        text_part.setText(result.get(0).getPart());

        text_sentence = viewGroup.findViewById(R.id.text_sentence);
        text_sentence.setText(result.get(0).getSentence());

        text_mean_s = viewGroup.findViewById(R.id.text_mean_s);
        text_mean_s.setText(result.get(0).getMean_s());

        image_word = viewGroup.findViewById(R.id.image_word);
        Glide.with(container.getContext())
                .load("http://13.209.75.148:5000/wordPic/1/" + result.get(0).getWord())
                .fitCenter()
                .into(image_word);

        btn_pron = viewGroup.findViewById(R.id.btn_pron); // 발음 듣기 버튼 할당
        btn_pron.setText(result.get(0).getWord());
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
                if(now_index == 0){
                    Toast myToast = Toast.makeText(getContext(),"이전 단어가 없습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else {
                    now_index--;
                    btn_pron.setText(result.get(now_index).getWord()); // 증가 시킨 인덱스의 단어로 텍스트 변경
                    text_mean.setText(result.get(now_index).getMean());
                    text_part.setText(result.get(now_index).getPart());
                    text_sentence.setText(result.get(now_index).getSentence());
                    text_mean_s.setText(result.get(now_index).getMean_s());
                    Glide.with(container.getContext())
                            .load("http://13.209.75.148:5000/wordPic/1/" + result.get(now_index).getWord())
                            .fitCenter()
                            .into(image_word);
                }
            }
        });

        btn_next = viewGroup.findViewById(R.id.btn_next); // 다음 버튼 할당
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(now_index == last_index){
                    Toast myToast = Toast.makeText(getContext(),"다음 단어가 없습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else {
                    now_index++;
                    btn_pron.setText(result.get(now_index).getWord()); // 증가 시킨 인덱스의 단어로 텍스트 변경
                    text_mean.setText(result.get(now_index).getMean());
                    text_part.setText(result.get(now_index).getPart());
                    text_sentence.setText(result.get(now_index).getSentence());
                    text_mean_s.setText(result.get(now_index).getMean_s());
                    Glide.with(container.getContext())
                            .load("http://13.209.75.148:5000/wordPic/1/" + result.get(now_index).getWord())
                            .fitCenter()
                            .into(image_word);
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
