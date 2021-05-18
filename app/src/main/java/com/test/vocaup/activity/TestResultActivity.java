package com.test.vocaup.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.vocaup.DO.Manager;
import com.test.vocaup.DO.Problem;
import com.test.vocaup.R;
import com.test.vocaup.adapter.checkAdapter;
import com.test.vocaup.server.Connect_put;

import java.util.ArrayList;

public class TestResultActivity extends AppCompatActivity {
    private ArrayList<Problem> result = new ArrayList<>();
    TextView percent;
    TextView OX;

    private Button btn_result;
    private Button btn_add;

    private checkAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testresult);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        Boolean ExamFlag = intent.getBooleanExtra("ExamFlag", false);
        Boolean RecapFlag = intent.getBooleanExtra("RecapFlag", false);
        percent = (TextView) findViewById(R.id.percent);
        OX = (TextView) findViewById(R.id.OX);

        btn_add = findViewById(R.id.btn_add);
        btn_result = findViewById(R.id.btn_result);

        Thread thread = new Thread() {
            @Override
            public void run() {
                result = (ArrayList<Problem>)getIntent().getExtras().get("test_result");
                int check = 0;
                for (Problem tmp_pro : result) {
                    if (tmp_pro.getAnswer() == tmp_pro.getChoice()) {
                        check++;
                    }
                }
                percent.setText("정답률 " + Math.round(((float) check / result.size()) * 100) + "%");
                OX.setText("O = " + check + ", X = " + (result.size() - check));
                if(ExamFlag && check >= result.size() * 0.9) {
                    Connect_put connect_put = new Connect_put();
                    Manager testManager = new Manager(((MenuActivity)MenuActivity.context).manager.getToken(),
                            ((MenuActivity)MenuActivity.context).manager.getLevel());
                    if (RecapFlag) {
                        System.out.println("test!!!");
                        testManager.setRecap(((MenuActivity)MenuActivity.context).manager.getRecap() + 1);
                    } else if (type.equals("blank_spelling")) {
                        testManager.setBlank_spelling(1);
                    } else if (type.equals("mean_spelling")) {
                        testManager.setMean_spelling(1);
                    } else if (type.equals("spelling_mean")) {
                        testManager.setSpelling_mean(1);
                    } else if (type.equals("spelling_mean_link")) {
                        testManager.setSpelling_mean_link(1);
                    } else if (type.equals("spelling_sort")) {
                        testManager.setSpelling_sort(1);
                    } else if (type.equals("pron_mean")) {
                        testManager.setPron_mean(1);
                    } else if (type.equals("cross_puz")) {
                        testManager.setCross_puz(1);
                    }

                    Manager resultManager = connect_put.changeUserInfo(testManager);
                    ((MenuActivity)MenuActivity.context).manager = ((MenuActivity)MenuActivity.context).getManager(); // Manager 객체 내용 업데이트

                    if(((MenuActivity)MenuActivity.context).manager.allClear()) {
                        testManager.setLevel(((MenuActivity)MenuActivity.context).manager.getLevel() + 1);
                        testManager.reset();

                        Manager result2Manager = connect_put.changeUserInfo(testManager);
                        ((MenuActivity)MenuActivity.context).manager = ((MenuActivity)MenuActivity.context).getManager(); // Manager 객체 내용 업데이트

                        System.out.println(((MenuActivity)MenuActivity.context).manager.getLevel());

                        btn_result.setOnClickListener(new ResultButtonLevelUpListener());
                    } else {
                        btn_result.setOnClickListener(new ResultButtonSuccessListener());
                    }
                } else if(ExamFlag)
                    btn_result.setOnClickListener(new ResultButtonFailureListener());
                else if(!ExamFlag)
                    btn_result.setOnClickListener(new ResultButtonBasicListener());

                btn_add.setOnClickListener(new AddButtonListener());
            }
        };

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // ViewGroup 내에서 View 찾음
        RecyclerView recyclerView = findViewById(R.id.recycler_item);
        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));

        // 세로방향으로 레이아웃 매니저 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getBaseContext(), LinearLayoutManager.VERTICAL, false
        );

        recyclerView.setLayoutManager(linearLayoutManager); // 레이아웃 매니저 등록

        adapter = new checkAdapter(); // 어댑터 객체 생성
        adapter.setItems(result); // 어댑터 아이템 설정

        recyclerView.setAdapter(adapter); // 어댑터 등록

        if (adapter.getWrongList().size() == 0) {
            btn_add.setEnabled(false);
            btn_add.setBackgroundResource(R.drawable.button_bg2);
        }
    }

    class ResultButtonLevelUpListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TestResultActivity.this);
                    builder.setTitle("레벨 업!");
                    builder.setMessage("축하합니다!! 이제 lv." + ((MenuActivity)MenuActivity.context).manager.getLevel() + "입니다!");
                    builder.setIcon(R.drawable.ic_success);

                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(TestResultActivity.this, "레벨 업", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }

    class ResultButtonSuccessListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TestResultActivity.this);
                    builder.setTitle("통과!");
                    builder.setMessage("축하합니다!! 남은 시험을 계속하세요.");
                    builder.setIcon(R.drawable.ic_success);

                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(TestResultActivity.this, "통과", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }

    class ResultButtonFailureListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TestResultActivity.this);
                    builder.setTitle("통과 실패!");
                    builder.setMessage("좀 더 학습이 필요합니다.");
                    builder.setIcon(R.drawable.ic_failure);

                    builder.setPositiveButton("돌아가기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(TestResultActivity.this, "통과 실패", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }

    class ResultButtonBasicListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TestResultActivity.this);
                    builder.setTitle("퀴즈 종료!");
                    builder.setMessage("메뉴로 돌아갑니다.");
                    builder.setIcon(R.drawable.ic_failure);

                    builder.setPositiveButton("돌아가기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(TestResultActivity.this, "학습 종료", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }

    class AddButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TestResultActivity.this);
                    builder.setTitle("나만의 단어장 추가!");
                    builder.setPositiveButton("추가", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            Toast.makeText(getApplicationContext(), "나만의 단어장에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                            ArrayList<String> strings = adapter.getWrongList();

                            Thread thread = new Thread() {
                                @Override
                                public void run() {
                                    new Connect_put(((MenuActivity)MenuActivity.context).manager.getToken())
                                            .appendWrongList(strings, ((MenuActivity)MenuActivity.context).manager.getLevel());
                                }
                            };

                            thread.start();

                            try {
                                thread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {

                        }
                    });
                    builder.setMessage("틀린문제를 나만의 단어장에 추가하시겠습니까?");
                    builder.setIcon(R.drawable.ic_failure);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        }
    }
}
