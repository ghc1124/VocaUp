package com.test.vocaup.quiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.vocaup.DO.Problem;
import com.test.vocaup.R;
import com.test.vocaup.activity.MenuActivity;
import com.test.vocaup.activity.TestResultActivity;
import com.test.vocaup.fragment.MenuFragment;
import com.test.vocaup.server.Connect_get;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CrossWord extends AppCompatActivity {
    EditText et[] = new EditText[100];
    EditText editText;
    private Button ok_btn;
    private Button submit_btn;
    private TextView mean_text;
    String wordTemp;
    int temp;
    int recentX, recentY;
    int target;

    String[] word = new String[]{null, null, null, null, null, null, null, null, null, null};
    String[] mean = new String[]{null, null, null, null, null, null, null, null, null, null};
    int[] x = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //x좌표
    int[] y = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //y좌표
    int[] len = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //길이
    boolean[] tst = new boolean[]{true, true, true, true, true, true, true, true, true, true}; //가로면 true 세로면 false

    int max_word = 1; //단어수

    private JSONObject result = new JSONObject();
    ArrayList<Problem> problem_list = new ArrayList<Problem>();
    int what_problem;
    int level_info;
    String test_json;

    private Boolean ExamFlag;
    private Boolean RecapFlag;

    Integer[] Rid_editText = {
            R.id.et00, R.id.et01, R.id.et02, R.id.et03, R.id.et04, R.id.et05, R.id.et06, R.id.et07, R.id.et08, R.id.et09, R.id.et10, R.id.et11, R.id.et12
            , R.id.et13, R.id.et14, R.id.et15, R.id.et16, R.id.et17, R.id.et18, R.id.et19, R.id.et20, R.id.et21, R.id.et22, R.id.et23, R.id.et24, R.id.et25
            , R.id.et26, R.id.et27, R.id.et28, R.id.et29, R.id.et30, R.id.et31, R.id.et32, R.id.et33, R.id.et34, R.id.et35, R.id.et36, R.id.et37, R.id.et38, R.id.et39, R.id.et40
            , R.id.et41, R.id.et42, R.id.et43, R.id.et44, R.id.et45, R.id.et46, R.id.et47, R.id.et48, R.id.et49, R.id.et50, R.id.et51, R.id.et52, R.id.et53, R.id.et54, R.id.et55
            , R.id.et56, R.id.et57, R.id.et58, R.id.et59, R.id.et60, R.id.et61, R.id.et62, R.id.et63, R.id.et64, R.id.et65, R.id.et66, R.id.et67, R.id.et68, R.id.et69, R.id.et70
            , R.id.et71, R.id.et72, R.id.et73, R.id.et74, R.id.et75, R.id.et76, R.id.et77, R.id.et78, R.id.et79, R.id.et80, R.id.et81, R.id.et82, R.id.et83, R.id.et84, R.id.et85
            , R.id.et86, R.id.et87, R.id.et88, R.id.et89, R.id.et90, R.id.et91, R.id.et92, R.id.et93, R.id.et94, R.id.et95, R.id.et96, R.id.et97, R.id.et98, R.id.et99
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_word);
        editText = findViewById(R.id.editText1);
        //editText.requestFocus();
        mean_text = findViewById(R.id.mean_text);

        what_problem = -1;
        test_json = "problem/cross_puz";
        target = -1;

        Intent intent = getIntent();
        ExamFlag = intent.getBooleanExtra("ExamFlag", false);
        RecapFlag = intent.getBooleanExtra("RecapFlag", false);
        level_info = intent.getIntExtra("levelInfo", 0);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                et[10 * i + j] = (EditText) findViewById(Rid_editText[10 * i + j]);
                et[10 * i + j].setOnTouchListener(new setOnTouchListener());
            }
        }


        Thread thread = new Thread() {
            @Override
            public void run() {
                result = new Connect_get(intent.getStringExtra("Token"))
                        .problem_get(test_json, (2 + ""));
                try {
                    next_problem(result, word, mean, x, y, len, tst);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //new Connect_get(intent.getStringExtra("Token")).updateSet();
            }
        };

        ok_btn = findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    View currentView = getCurrentFocus();
                    for (int i = 0; i < 100; i++) {
                        if (Rid_editText[i] == currentView.getId()) {
                            temp = i;
                        }
                    }
                    recentX = temp % 10;
                    recentY = temp / 10;

                    wordTemp = editText.getText().toString();

                    System.out.println(target);

                    if (target != -1) {
                        if (!tst[target]) {
                            for (int i = 0; i < len[target]; i++) {
                                et[x[target] * 10 + y[target] + i].setText(wordTemp.charAt(i) + "");
                                System.out.println("좌표:" + (x[target] * 10 + y[target] + i));
                            }
                        } else {
                            for (int i = 0; i < len[target]; i++) {
                                et[(x[target] + i) * 10 + y[target]].setText(wordTemp.charAt(i) + "");
                                System.out.println("우표:" + (x[target] * 10 + y[target] + i));
                            }
                        }
                    }
                    editText.setText("");

                } catch (Exception e) {
                }
            }
        });

        submit_btn = findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (what_problem <= result.getJSONArray("problem_list").length() - 2) {
                        checking_problem(word, mean, x, y, len, tst, problem_list);
                        next_problem(result, word, mean, x, y, len, tst);
                    } else {
                        Intent intent = new Intent(CrossWord.this, TestResultActivity.class);
                        intent.putExtra("test_result", problem_list);
                        intent.putExtra("type", "cross_puz");
                        intent.putExtra("ExamFlag", ExamFlag);
                        intent.putExtra("RecapFlag", RecapFlag);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                String temp="";
//                temp=editText.getText().toString();
//
//                et[x1 * 10 + y1].setText("" + temp.charAt(0));
//
//                mean_text.setText(tmean); //옮겨야함
//
//                if (temp.length() == len){
//                    for (int i = 1; i < len; i++) {
//                        if (tst == true) {
//                            et[x1 * 10 + y1 + i].setText("" + temp.charAt(i));
//                        } else {
//                            et[(x1 + i) * 10 + y1].setText("" + temp.charAt(i));
//                        }
//                    }
//                }
//                else
//                    Toast.makeText(CrossWord.this, "길이가 달라요! ", Toast.LENGTH_SHORT).show();
            }
        });


        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class setOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (target != -1) {
                if (!tst[target]) {
                    for (int j = 0; j < len[target]; j++) {
                        et[x[target] * 10 + y[target] + j].setBackground(getResources().getDrawable(R.drawable.edittext_rounded_corner));
                    }
                } else
                    for (int j = 0; j < len[target]; j++) {
                        et[(x[target] + j) * 10 + y[target]].setBackground(getResources().getDrawable(R.drawable.edittext_rounded_corner));
                    }
            }


            target = -1;
            System.out.println("시작");
            for (int i = 0; i < 100; i++) {
                if (Rid_editText[i] == view.getId()) {
                    temp = i;
                }
            }
            recentX = temp / 10;
            recentY = temp % 10;

            for (int i = 0; i < max_word; i++) {
                if (tst[i]) {
                    for (int j = 0; j < len[i]; j++) {
                        if (((x[i] + j) == recentX) && (y[i] == recentY)) {
                            if (target == -1)
                                target = i;
                            else
                                target = -1;
                        }


                    }

                } else {
                    for (int j = 0; j < len[i]; j++) {
                        if (((x[i]) == recentX) && ((y[i] + j) == recentY)) {
                            if (target == -1)
                                target = i;
                            else
                                target = -1;
                        }
                    }
                }
            }
            System.out.println(target + "번째 단어");
            if(target!=-1)
                mean_text.setText(mean[target]);

            if (target != -1) {
                if (!tst[target]) {
                    for (int i = 0; i < len[target]; i++) {
                        et[x[target] * 10 + y[target] + i].setBackground(getResources().getDrawable(R.drawable.edittext_select_rounded_corner));

                    }
                } else {
                    for (int i = 0; i < len[target]; i++) {
                        et[(x[target] + i) * 10 + y[target]].setBackground(getResources().getDrawable(R.drawable.edittext_select_rounded_corner));

                    }
                }

                editText.requestFocus();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        String data = textView.getText().toString();

                        try {
                            View currentView = getCurrentFocus();
                            for (int j = 0; j < 100; j++) {
                                if (Rid_editText[j] == currentView.getId()) {
                                    temp = j;
                                }
                            }
                            recentX = temp % 10;
                            recentY = temp / 10;

                            wordTemp = editText.getText().toString();

                            System.out.println(target);

                            if (target != -1) {
                                if (!tst[target]) {
                                    for (int k = 0; k < len[target]; k++) {
                                        et[x[target] * 10 + y[target] + k].setText(wordTemp.charAt(k) + "");
                                        System.out.println("좌표:" + (x[target] * 10 + y[target] + k));
                                    }
                                } else {
                                    for (int l = 0; l < len[target]; l++) {
                                        et[(x[target] + l) * 10 + y[target]].setText(wordTemp.charAt(l) + "");
                                        System.out.println("우표:" + (x[target] * 10 + y[target] + l));
                                    }
                                }
                            }
                            editText.setText("");

                        } catch (Exception e) {
                        }

                        textView.clearFocus();
                        textView.setFocusable(false);
                        textView.setText("");
                        textView.setFocusableInTouchMode(true);
                        textView.setFocusable(true);

                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                        return true;
                    }
                });

            }
            return true;
        }
    }


    protected void next_problem(JSONObject result, String[] word, String[] mean, int[] x, int[] y, int[] len, boolean[] tst) throws JSONException {
        JSONArray tmp_array = result.getJSONArray("problem_list");
        what_problem++;
        JSONArray target_exam = (JSONArray) tmp_array.get(what_problem);
        max_word = target_exam.length();

        for (int i = 0; i < et.length; i++) {
            et[i].setEnabled(false);
        }

        for (int i = 0; i < max_word; i++) {
            JSONObject tmp_object = target_exam.getJSONObject(i);
            word[i] = tmp_object.getString("word");
            mean[i] = tmp_object.getString("mean");
            x[i] = tmp_object.getInt("x");
            y[i] = tmp_object.getInt("y");
            len[i] = word[i].length();
            tst[i] = tmp_object.getBoolean("position");
        }

        for (int i = 0; i < max_word; i++) {
            if (tst[i]) {
                for (int j = 0; j < len[i]; j++) {
                    et[(x[i] + j) * 10 + y[i]].setEnabled(true);
                }

            } else {
                for (int j = 0; j < len[i]; j++) {
                    et[x[i] * 10 + y[i] + j].setEnabled(true);
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("").setMessage("포기하시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                CrossWord.super.onBackPressed();
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

    public void checking_problem(String[] word, String[] mean, int[] x, int[] y, int[] len, boolean[] tst, ArrayList<Problem> problem_list) {
        for (int i = 0; i < max_word; i++) {
            Problem problem_add = new Problem();
            int check_ox = 0;//일단 정답으로 간주 보기로 간주하는 select 배열에 0번째는 답 1번째는 사용자가 쓴 문자열 기입
            String user_answer = "";
            if (tst[i]) {
                for (int j = 0; j < len[i]; j++) {
                    user_answer = user_answer + et[(x[i] + j) * 10 + y[i]].getText().toString();
                    if (et[(x[i] + j) * 10 + y[i]].getText().toString().length() != 0 && (word[i].charAt(j) != et[(x[i] + j) * 10 + y[i]].getText().toString().charAt(0))) {
                        System.out.println("비교할 거 :" + word[i].charAt(j) + "유저선택" + et[(x[i] + j) * 10 + y[i]].getText().toString().charAt(0));
                        check_ox = 1;
                    }
                    if (et[(x[i] + j) * 10 + y[i]].getText().toString().length() == 0) {
                        user_answer = user_answer + "*";
                        check_ox = 1;
                    }
                }

            } else {
                for (int j = 0; j < len[i]; j++) {
                    user_answer = user_answer + et[x[i] * 10 + y[i] + j].getText().toString();
                    if (et[x[i] * 10 + y[i] + j].getText().toString().length() != 0 && (word[i].charAt(j) != et[x[i] * 10 + y[i] + j].getText().toString().charAt(0))) {
                        System.out.println("비교할 거 :" + word[i].charAt(j) + "유저선택" + et[x[i] * 10 + y[i] + j].getText().toString().charAt(0));
                        check_ox = 1;
                    }
                    if (et[x[i] * 10 + y[i] + j].getText().toString().length() == 0) {
                        user_answer = user_answer + "*";
                        check_ox = 1;
                    }
                }
            }
            problem_add.setShow(mean[i]);
            problem_add.addSelect(word[i]);
            problem_add.addSelect(user_answer);
            problem_add.setAnswer(0);
            problem_add.setChoice(check_ox);

            problem_list.add(problem_add);
        }
    }
}