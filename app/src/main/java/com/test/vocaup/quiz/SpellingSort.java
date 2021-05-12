package com.test.vocaup.quiz;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.test.vocaup.DO.Problem;
import com.test.vocaup.R;
import com.test.vocaup.activity.MenuActivity;
import com.test.vocaup.activity.TestResultActivity;
import com.test.vocaup.server.Connect_get;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpellingSort extends AppCompatActivity {
    private JSONObject result = new JSONObject();
    private ArrayList<Problem> problem_list = new ArrayList<Problem>();
    private int what_problem;
    private int level_info;
    private String test_json;
    private TextView answer;
    private TextView mean;
    private String alphabet;
    private TableLayout but_array;
    private int user_cursor;
    private TextView textView_count;

    private Boolean ExamFlag;
    private Boolean RecapFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_sort);

        mean =(TextView)findViewById(R.id.mean);
        answer = (TextView)findViewById(R.id.answer);
//        but_array = (LinearLayout)findViewById(R.id.button_layout);
        but_array = findViewById(R.id.button_layout);

        user_cursor = 0;
        what_problem = -1;

        textView_count = findViewById(R.id.textView_count);

        test_json = "problem/spelling_sort";
        SelectBtnOnClickListener but_listener = new SelectBtnOnClickListener();

        Intent intent = getIntent();
        ExamFlag = intent.getBooleanExtra("ExamFlag", false);
        RecapFlag = intent.getBooleanExtra("RecapFlag", false);
        level_info = intent.getIntExtra("levelInfo", 0);

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

        textView_count.setText("1/" + problem_list.size());
    }

    class SelectBtnOnClickListener implements Button.OnClickListener {
        @SuppressLint("ResourceType")
        @Override
        public void onClick(View view) {
//            View v = but_array.getChildAt(view.getId());
//            System.out.println(v.getId());
            Button tmp_button = (Button)findViewById(view.getId());
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

                for(int i=0;i<problem_list.size();i++){
                    if(problem_list.get(i).getSelect(0).equals(problem_list.get(i).getSelect(2)))
                        problem_list.get(i).setChoice(0);
                }

                Intent intent = new Intent(SpellingSort.this, TestResultActivity.class);
                intent.putExtra("test_result",problem_list);
                intent.putExtra("type", "spelling_sort");
                intent.putExtra("ExamFlag", ExamFlag);
                intent.putExtra("RecapFlag", RecapFlag);
                startActivity(intent);
            }
            tmp_button.setEnabled(false);
        }
    }

    protected void problem_list_fill(JSONObject result_object, ArrayList<Problem> problem_list) throws JSONException {
        JSONArray tmp_array = result_object.getJSONArray("problem_list");
        for (int i = 0; i < tmp_array.length(); i++) {
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
        String count_underbar = "";
        int length = 0;
        user_cursor = 0;
        what_problem++;

        if ((what_problem + 1) <= problem_list.size())
            textView_count.setText((what_problem + 1) + "/" + problem_list.size());

        mean.setText(problem_list.get(what_problem).getShow());
        length = problem_list.get(what_problem).getSentence().length();
        alphabet = problem_list.get(what_problem).getSelect(1);
        for (int i = 0; i < length; i++) {
            count_underbar += "_";
        }
        answer.setText(count_underbar);
        but_array.removeAllViews();
        but_array.setGravity(Gravity.CENTER);
        int count = 0;
        int count2 = 0;
//        for(char alpha:alphabet.toCharArray()){
//            Button tmp_but = new Button(this);
//            tmp_but.setAllCaps(false);
//            tmp_but.setId(count++);
//            tmp_but.setText(alpha+"");
//            tmp_but.setOnClickListener(but_listener);
//            but_array.addView(tmp_but);
//        }

        TableRow[] tableRows = new TableRow[4];
        for(int i = 0; i < tableRows.length; i++) {
            tableRows[i] = new TableRow(this);
            tableRows[i].setGravity(Gravity.CENTER);
            tableRows[i].setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
        }

        for(char alpha:alphabet.toCharArray()){
            Button tmp_but = new Button(this);
            tmp_but.setAllCaps(false);
            tmp_but.setId(count++);
            tmp_but.setText(alpha+"");
            tmp_but.setOnClickListener(but_listener);
            //System.out.println(alpha);
            //System.out.println(count2 / 5);
            tableRows[count2++ / 4].addView(tmp_but);
        }

        for(int i = 0; i < count2 / 4 + 1; i++) {
            but_array.addView(tableRows[i]);
        }

        count2 = 0;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("").setMessage("포기하시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                SpellingSort.super.onBackPressed();
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
}