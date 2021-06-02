package com.test.vocaup.quiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.test.vocaup.DO.Problem;
import com.test.vocaup.R;
import com.test.vocaup.activity.MenuActivity;
import com.test.vocaup.activity.TestResultActivity;
import com.test.vocaup.fragment.MyWordListFragment;
import com.test.vocaup.fragment.StudyFragment;
import com.test.vocaup.fragment.StudyListFragment;
import com.test.vocaup.server.Connect_get;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BlankSpelling extends AppCompatActivity {
    private JSONObject result = new JSONObject();
    private ArrayList<Problem> problem_list = new ArrayList<Problem>();
    private int what_problem;
    private int level_info;
    private String test_json;
    private TextView sentence;
    private Button[] but_array;
    private TextView textView_count;
    private TextView textView_info;

    private Boolean ExamFlag;
    private Boolean RecapFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_spelling);
        sentence = (TextView) findViewById(R.id.look);
        but_array = new Button[4];
        but_array[0] = (Button) findViewById(R.id.button0);
        but_array[1] = (Button) findViewById(R.id.button1);
        but_array[2] = (Button) findViewById(R.id.button2);
        but_array[3] = (Button) findViewById(R.id.button3);
        what_problem = -1;

        textView_count = findViewById(R.id.textView_count);
        textView_info = findViewById(R.id.textView_info);

        test_json = "problem/blank_spelling";
        SelectBtnOnClickListener but_listener = new SelectBtnOnClickListener();

        Intent intent = getIntent();
        ExamFlag = intent.getBooleanExtra("ExamFlag", false);
        RecapFlag = intent.getBooleanExtra("RecapFlag", false);
        level_info = intent.getIntExtra("levelInfo", 0);

        Thread thread = new Thread() {
            @Override
            public void run() {
                result = new Connect_get(intent.getStringExtra("Token"))
                        .problem_get(test_json, (level_info + ""));
                try {
                    problem_list_fill(result, problem_list);
                    next_problem(sentence, but_array, problem_list);
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

        try {
            textView_info.setText("문제 정보: " + result.getString("meta").substring(0, result.getString("meta").length() - 5));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 4; i++) {
            but_array[i].setOnClickListener(but_listener);
        }

    }

    class SelectBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            int click_but = -1;
            switch (view.getId()) {
                case R.id.button0:
                    click_but = 0;
                    break;
                case R.id.button1:
                    click_but = 1;
                    break;
                case R.id.button2:
                    click_but = 2;
                    break;
                case R.id.button3:
                    click_but = 3;
                    break;
            }
            System.out.println(problem_list.size() + " : " + what_problem);
            problem_list.get(what_problem).setChoice(click_but);

            if (what_problem <= problem_list.size() - 2) {
                next_problem(sentence, but_array, problem_list);
            } else {
                Intent intent = new Intent(BlankSpelling.this, TestResultActivity.class);
                intent.putExtra("test_result", problem_list);
                intent.putExtra("type", "blank_spelling");
                intent.putExtra("ExamFlag", ExamFlag);
                intent.putExtra("RecapFlag", RecapFlag);
                startActivity(intent);
            }
        }
    }

    protected void problem_list_fill(JSONObject result_object, ArrayList<Problem> problem_list) throws JSONException {
        JSONArray tmp_array = result_object.getJSONArray("problem_list");
        for (int i = 0; i < tmp_array.length(); i++) {
            Problem problem_add = new Problem();
            JSONObject problem_single = (JSONObject) tmp_array.get(i);
            JSONArray select_list = problem_single.getJSONArray("select");
            //problem_single.getString("sentence");
            String insert_sentence = problem_single.getString("sentence");

            problem_add.setAnswer(problem_single.getInt("answer"));
            problem_add.setShow(problem_single.getString("show"));
            problem_add.setSentence(insert_sentence);

            for (int j = 0; j < select_list.length(); j++) {
                problem_add.addSelect(select_list.getString(j));
            }
            problem_list.add(problem_add);
        }
    }

    protected void next_problem(TextView sentence, Button[] buttons, ArrayList<Problem> problem_list) {
        what_problem++;

        if ((what_problem + 1) <= problem_list.size())
            textView_count.setText((what_problem + 1) + "/" + problem_list.size());

        sentence.setText(problem_list.get(what_problem).getSentence());

        for (int i = 0; i < problem_list.get(what_problem).getSelectSize(); i++) {
            buttons[i].setText(problem_list.get(what_problem).getSelect(i));
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("").setMessage("포기하시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                BlankSpelling.super.onBackPressed();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}