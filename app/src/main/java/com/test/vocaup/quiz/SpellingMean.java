package com.test.vocaup.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.test.vocaup.DO.Problem;
import com.test.vocaup.R;
import com.test.vocaup.activity.TestResultActivity;
import com.test.vocaup.server.Connect_get;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpellingMean extends AppCompatActivity {
    private JSONObject result = new JSONObject();
    ArrayList<Problem> problem_list= new ArrayList<Problem>();
    int what_problem;
    int level_info;
    String test_json;
    TextView spelling;
    Button[] but_array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_mean);
        spelling =  (TextView)findViewById(R.id.look);
        but_array = new Button[4];
        but_array[0] = (Button)findViewById(R.id.button0);
        but_array[1] = (Button)findViewById(R.id.button1);
        but_array[2] = (Button)findViewById(R.id.button2);
        but_array[3] = (Button)findViewById(R.id.button3);
        what_problem = -1;
        level_info = 10;
        test_json = "problem/spelling_mean";
        SelectBtnOnClickListener but_listener = new SelectBtnOnClickListener();

        Intent intent = getIntent();

        Thread thread = new Thread() {
            @Override
            public void run() {
                result = new Connect_get(intent.getStringExtra("Token"))
                        .problem_get(test_json, (level_info+""));
                try {
                    problem_list_fill(result,problem_list);
                    next_problem(spelling, but_array, problem_list);
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
            but_array[i].setOnClickListener(but_listener);
        }

    }
    class SelectBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            int click_but = -1;
            switch (view.getId()) {
                case R.id.button0 :
                    click_but = 0;
                    break ;
                case R.id.button1 :
                    click_but = 1;
                    break ;
                case R.id.button2 :
                    click_but = 2;
                    break ;
                case R.id.button3 :
                    click_but = 3;
                    break ;
            }
            System.out.println(problem_list.size()+ " : "+ what_problem);
            problem_list.get(what_problem).setChoice(click_but);
            if(problem_list.get(what_problem).getAnswer()==click_but){
                problem_list.get(what_problem).setCheck(true);
            }
            if(what_problem<=problem_list.size()-2) {
                next_problem(spelling, but_array, problem_list);
            }
            else{
                Intent intent = new Intent(SpellingMean.this, TestResultActivity.class);
                intent.putExtra("test_result",problem_list);
                startActivity(intent);
            }
        }
    }

    protected void problem_list_fill(JSONObject result_object, ArrayList<Problem> problem_list) throws JSONException {
        JSONArray tmp_array= result_object.getJSONArray("problem_list");
        for(int i=0;i<tmp_array.length();i++){
            Problem problem_add = new Problem();
            JSONObject problem_single = (JSONObject) tmp_array.get(i);
            JSONArray select_list = problem_single.getJSONArray("select");
            problem_add.setAnswer(problem_single.getInt("answer"));
            problem_add.setShow(problem_single.getString("show"));
            for(int j=0;j<select_list.length();j++){
                problem_add.addSelect(select_list.getString(j));
            }
            problem_list.add(problem_add);
        }
    }

    protected void next_problem(TextView show, Button[] buttons, ArrayList<Problem> problem_list){
        what_problem++;
        show.setText(problem_list.get(what_problem).getShow());
        for(int i=0 ; i< problem_list.get(what_problem).getSelectSize() ; i++){
            buttons[i].setText(problem_list.get(what_problem).getSelect(i));
        }

    }
}