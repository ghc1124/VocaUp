package com.test.vocaup.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.test.vocaup.DO.ListAll;
import com.test.vocaup.R;
import com.test.vocaup.activity.MenuActivity;
import com.test.vocaup.server.Connect_get;
import com.test.vocaup.server.Connect_put;

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
    private CheckBox checkBox_single;
    private MediaPlayer mediaPlayer;

    private int now_index = 0;
    private int last_index = 0;

    private myListener listener = new myListener();

    public static AllWordSinglyFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new AllWordSinglyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_mywordlistsingly, container, false);

        String level = ((MenuActivity)MenuActivity.context).manager.getLevel() + "";

        Thread thread = new Thread() {
            @Override
            public void run() {
                result = new Connect_get(((MenuActivity)MenuActivity.context).manager.getToken())
                        .get("list", level);
            }
        };

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(result.size() > 0) {
            last_index = result.size() - 1;

            checkBox_single = viewGroup.findViewById(R.id.checkBox_single);
            checkBox_single.setOnCheckedChangeListener(null);

            text_mean = viewGroup.findViewById(R.id.text_mean);
            checkBox_single.setChecked(result.get(0).isSelected());

            text_mean.setText(result.get(0).getMean());

            text_part = viewGroup.findViewById(R.id.text_part);
            text_part.setText(result.get(0).getPart());

            text_sentence = viewGroup.findViewById(R.id.text_sentence);
            text_sentence.setText(result.get(0).getSentence());

            text_mean_s = viewGroup.findViewById(R.id.text_mean_s);
            text_mean_s.setText(result.get(0).getMean_s());

            btn_pron = viewGroup.findViewById(R.id.btn_pron); // 발음 듣기 버튼 할당
            btn_pron.setText(result.get(0).getWord());

            image_word = viewGroup.findViewById(R.id.image_word);
            Glide.with(container.getContext())
                    .load("http://13.209.75.148:5000/wordPic/" + level + "/" + result.get(0).getOrigin_word())
                    .fitCenter()
                    .into(image_word);

            checkBox_single.setOnCheckedChangeListener(listener);
        }

        btn_pron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(mediaPlayer != null) {
                        mediaPlayer.stop();
                    }

                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource("http://13.209.75.148:5000/wordPron/" + level + "/" + btn_pron.getText() + ".mp3");
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            Log.e("PREPARED", "Start Music");
                            mediaPlayer.start();
                        }
                    });
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_back = viewGroup.findViewById(R.id.btn_back); // 뒤로 버튼 할당
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(now_index == 0){
                    Toast myToast = Toast.makeText(getContext(),"이전 단어가 없습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                } else {
                    now_index--;
                    checkBox_single.setOnCheckedChangeListener(null);
                    checkBox_single.setChecked(result.get(now_index).isSelected());
                    checkBox_single.setOnCheckedChangeListener(listener);
                    btn_pron.setText(result.get(now_index).getWord()); // 증가 시킨 인덱스의 단어로 텍스트 변경
                    text_mean.setText(result.get(now_index).getMean());
                    text_part.setText(result.get(now_index).getPart());
                    text_sentence.setText(result.get(now_index).getSentence());
                    text_mean_s.setText(result.get(now_index).getMean_s());
                    Glide.with(container.getContext())
                            .load("http://13.209.75.148:5000/wordPic/" + level + "/" + result.get(now_index).getOrigin_word())
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
                } else {
                    now_index++;
                    btn_pron.setText(result.get(now_index).getWord()); // 증가 시킨 인덱스의 단어로 텍스트 변경
                    checkBox_single.setOnCheckedChangeListener(null);
                    checkBox_single.setChecked(result.get(now_index).isSelected());
                    checkBox_single.setOnCheckedChangeListener(listener);
                    text_mean.setText(result.get(now_index).getMean());
                    text_part.setText(result.get(now_index).getPart());
                    text_sentence.setText(result.get(now_index).getSentence());
                    text_mean_s.setText(result.get(now_index).getMean_s());
                    Glide.with(container.getContext())
                            .load("http://13.209.75.148:5000/wordPic/" + level + "/" + result.get(now_index).getOrigin_word())
                            .fitCenter()
                            .into(image_word);
                }
            }
        });

        return viewGroup;
    }



    @Override
    public void onBack() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("").setMessage("뒤로 가시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                // MainFragment 로 교체
                ((MenuActivity) getActivity()).replaceFragment(StudyListFragment.newInstance());
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // Fragment 호출 시 반드시 호출되는 오버라이드 메소드
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Other", "onAttach()");
        ((MenuActivity)context).setOnBackPressedListener(this);
    }

    private class myListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b) {
                new AllWordFragment().onClick(result.get(now_index).getIndex());
            } else {
                new AllWordFragment().onClick(-1 * result.get(now_index).getIndex());
            }

            result.get(now_index).setSelected(b);
        }
    }
}
