package com.test.vocaup.quiz;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.test.vocaup.DO.Problem;
import com.test.vocaup.R;
import com.test.vocaup.activity.TestResultActivity;
import com.test.vocaup.draw.Link_line;
import com.test.vocaup.server.Connect_get;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpellingMeanLink extends AppCompatActivity {
    private JSONObject result = new JSONObject();
    ArrayList<Problem> problem_list= new ArrayList<Problem>();
    int what_problem;
    int level_info;
    String test_json;
    TextView spelling;
    int[] OX;
    Button[] Show_but_array;
    Button[] Select_but_array;
    Link_line line;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_mean_link);
        spelling =  (TextView)findViewById(R.id.look);
        Show_but_array = new Button[4];
        Select_but_array = new Button[4];
        OX = new int[6];                                                //5번째와 6번째는 show, select 버튼클릭유무체크
        OX[0] = OX[1] = OX[2] = OX[3] = OX[4] = OX[5] = -1;
        Show_but_array[0] = (Button)findViewById(R.id.show_0);
        Show_but_array[1] = (Button)findViewById(R.id.show_1);
        Show_but_array[2] = (Button)findViewById(R.id.show_2);
        Show_but_array[3] = (Button)findViewById(R.id.show_3);
        Select_but_array[0] = (Button)findViewById(R.id.select_0);
        Select_but_array[1] = (Button)findViewById(R.id.select_1);
        Select_but_array[2] = (Button)findViewById(R.id.select_2);
        Select_but_array[3] = (Button)findViewById(R.id.select_3);
        line = (Link_line)findViewById(R.id.line);
        what_problem = -1;
        level_info = 10;
        test_json = "problem/spelling_mean_link";
        SpellingMeanLink.SelectBtnOnClickListener but_listener = new SpellingMeanLink.SelectBtnOnClickListener();
      //  line.setting(OX[0],OX[1],OX[2],OX[3]);
        Intent intent = getIntent();

        Thread thread = new Thread() {
            @Override
            public void run() {
                result = new Connect_get(intent.getStringExtra("Token"))
                        .problem_get(test_json, (level_info+""));
                try {
                    problem_list_fill(result,problem_list);
                    next_problem(spelling, Show_but_array,Select_but_array, problem_list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //new Connect_get(intent.getStringExtra("Token")).updateSet();
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < 4; i++){
            Show_but_array[i].setOnClickListener(but_listener);
            Select_but_array[i].setOnClickListener(but_listener);
        }

    }

    class SelectBtnOnClickListener implements Button.OnClickListener {
        @Override

        public void onClick(View view) {
            int click_but = -1;
            for(int i=0;i<problem_list.size();i++){
                System.out.println(problem_list.get(i).getAnswer() + " : " + problem_list.get(i).getChoice());
            }
            switch (view.getId()) {
                case R.id.show_0:
                    OX[4]=0;
                    break ;
                case R.id.show_1 :
                    OX[4]=1;
                    break ;
                case R.id.show_2 :
                    OX[4]=2;
                    break ;
                case R.id.show_3 :
                    OX[4]=3;
                    break ;
                case R.id.select_0 :
                    OX[5] = 0;
                    break ;
                case R.id.select_1 :
                    OX[5] = 1;
                    break ;
                case R.id.select_2 :
                    OX[5] = 2;
                    break ;
                case R.id.select_3 :
                    OX[5] = 3;
                    break ;


            }

            if(OX[4]!=-1 && OX[5]!=-1){
                OX[OX[4]]=OX[5];
                problem_list.get(what_problem*4+OX[4]).setChoice(OX[5]);
                line.update(OX);
                OX[4] = -1;
                OX[5] = -1;
            }

            System.out.println();
            if(what_problem<=(problem_list.size()/4)-2 && (OX[0]!=-1 && OX[1]!=-1 && OX[2]!=-1 && OX[3]!=-1) ) {
                next_problem(spelling, Show_but_array,Select_but_array, problem_list);
                line.update(OX);
            }
            else if(!(what_problem<=(problem_list.size()/4)-2)&&(OX[0]!=-1 && OX[1]!=-1 && OX[2]!=-1 && OX[3]!=-1)){
                Intent intent = new Intent(SpellingMeanLink.this, TestResultActivity.class);
                intent.putExtra("test_result",problem_list);
                startActivity(intent);
            }
        }
    }



    //문제리스트를 받아서 배열에 저장함
    protected void problem_list_fill(JSONObject result_object, ArrayList<Problem> problem_list) throws JSONException {
        JSONArray tmp_array= result_object.getJSONArray("problem_list");
        for(int i=0;i<tmp_array.length();i++){
            JSONObject problem_single = (JSONObject) tmp_array.get(i);
            JSONArray select_list = problem_single.getJSONArray("select");
            JSONArray answer_list = problem_single.getJSONArray("answer");
            JSONArray show_list = problem_single.getJSONArray("show");

            for(int j=0;j<select_list.length();j++){
                Problem problem_add = new Problem();
                for(int k=0;k<select_list.length();k++){
                    problem_add.addSelect(select_list.getString(k));
                }
                problem_add.setShow(show_list.getString(j));
                problem_add.setAnswer(answer_list.getInt(j));
                problem_list.add(problem_add);
            }
        }
    }
    //다음문제로 세팅
    protected void next_problem(TextView show, Button[] show_buttons, Button[] select_buttons, ArrayList<Problem> problem_list){
        what_problem++;
        for(int i=0 ; i< problem_list.get(what_problem).getSelectSize() ; i++){
            show_buttons[i].setText(problem_list.get(what_problem*4+i).getShow());
            select_buttons[i].setText(problem_list.get(what_problem*4+i).getSelect(i));
        }
        OX[0] = OX[1] = OX[2] = OX[3] = OX[4] = OX[5] = -1;
    }



}