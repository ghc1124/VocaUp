package com.test.vocaup.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.test.vocaup.DO.Problem;
import com.test.vocaup.R;
import com.test.vocaup.activity.TestResultActivity;
import com.test.vocaup.server.Connect_get;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpellingSort extends AppCompatActivity {
    private JSONObject result = new JSONObject();
    ArrayList<Problem> problem_list= new ArrayList<Problem>();
    int what_problem;
    int level_info;
    String test_json;
    TextView answer;
    TextView mean;
    String alphabet;
    LinearLayout but_array;
    int user_cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_sort);

        mean =(TextView)findViewById(R.id.mean);
        answer = (TextView)findViewById(R.id.answer);
        but_array = (LinearLayout)findViewById(R.id.button_layout);

        user_cursor = 0;
        what_problem = -1;
        level_info = 10;
        test_json = "problem/spelling_sort";
        SelectBtnOnClickListener but_listener = new SelectBtnOnClickListener();

        Intent intent = getIntent();

        Thread thread = new Thread() {
            @Override
            public void run() {
                result = new Connect_get(intent.getStringExtra("Token"))
                        .problem_get(test_json, (level_info+""));
                try {
                    problem_list_fill(result,problem_list);
                    next_problem(mean, answer, problem_list);
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
    }

    class SelectBtnOnClickListener implements Button.OnClickListener {
        @SuppressLint("ResourceType")
        @Override
        public void onClick(View view) {
            View v = but_array.getChildAt(view.getId());
            Button tmp_button = (Button)findViewById(v.getId());
            StringBuilder tmp_answer = new StringBuilder(answer.getText().toString());
            tmp_answer.setCharAt(user_cursor, tmp_button.getText().charAt(0));
            answer.setText(tmp_answer);

            user_cursor ++;
            if(answer.getText().length()<=user_cursor && what_problem<=problem_list.size()-2){
                problem_list.get(what_problem).addSelect(answer.getText()+"");
                next_problem(mean, answer, problem_list);
            }
            else if((what_problem==problem_list.size()-1) && user_cursor == answer.getText().length()){
                problem_list.get(what_problem).addSelect(answer.getText()+"");
                Intent intent = new Intent(SpellingSort.this, TestResultActivity.class);
                intent.putExtra("test_result",problem_list);
                startActivity(intent);
            }
            tmp_button.setEnabled(false);
        }
    }

    protected void problem_list_fill(JSONObject result_object, ArrayList<Problem> problem_list) throws JSONException {
        JSONArray tmp_array= result_object.getJSONArray("problem_list");
        for(int i=0;i<tmp_array.length();i++){
            Problem problem_add = new Problem();
            JSONObject problem_single = (JSONObject) tmp_array.get(i);

            problem_add.setSentence(problem_single.getString("answer"));
            problem_add.setShow(problem_single.getString("show"));
            problem_add.setAnswer(0);
            problem_add.setChoice(2);
            problem_add.addSelect(problem_add.getSentence());
            problem_add.addSelect(problem_single.getString("alphabet"));    
            //0 : 정답 스펠링 1: 알파벳들 2 : 이후 사용자가 적은 정답기입

            problem_list.add(problem_add);
        }
    }

    protected void next_problem(TextView mean, TextView answer, ArrayList<Problem> problem_list){
        SelectBtnOnClickListener but_listener = new SelectBtnOnClickListener();
        String count_underbar="";
        int length = 0;
        user_cursor = 0;
        what_problem++;
        mean.setText(problem_list.get(what_problem).getShow());
        length = problem_list.get(what_problem).getSentence().length();
        alphabet= problem_list.get(what_problem).getSelect(1);
        for(int i=0;i<length;i++){
            count_underbar+="_";
        }
        answer.setText(count_underbar);
        but_array.removeAllViews();
        int count=0;
        for(char alpha:alphabet.toCharArray()){
            Button tmp_but = new Button(this);
            tmp_but.setAllCaps(false);
            tmp_but.setId(count++);
            tmp_but.setText(alpha+"");
            tmp_but.setOnClickListener(but_listener);
            but_array.addView(tmp_but);
        }
        
        
    }
}