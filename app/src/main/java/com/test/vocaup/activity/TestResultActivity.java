package com.test.vocaup.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.ViewGroup;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testresult);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        Boolean ExamFlag = intent.getBooleanExtra("ExamFlag", false);

        Thread thread = new Thread() {
            @Override
            public void run() {
                result = (ArrayList<Problem>)getIntent().getExtras().get("test_result");
                int check=0;
                for(Problem tmp_pro:result){
                    if(tmp_pro.getAnswer()==tmp_pro.getChoice()){
                        check++;
                    }
                }
                if(ExamFlag && check >= result.size() * 0.9) {
                    Connect_put connect_put = new Connect_put();
                    Manager testManager = new Manager(((MenuActivity)MenuActivity.context).token);
                    if (type.equals("blank_spelling")) {
                        testManager.setBlank_spelling(1);
                    } else if (type.equals("mean_spelling")) {
                        testManager.setMean_spelling(1);
                    } else if (type.equals("spelling_mean")) {
                        testManager.setSpelling_mean(1);
                    } else if (type.equals("spelling_mean_link")) {
                        testManager.setSpelling_mean_link(1);
                    } else if (type.equals("spelling_sort")) {
                        testManager.setSpelling_sort(1);
                    }

                    Manager resultManager = connect_put.changeUserInfo(testManager);
                    ((MenuActivity)MenuActivity.context).manager = ((MenuActivity)MenuActivity.context).getManager(); // Manager 객체 내용 업데이트

                    if(((MenuActivity)MenuActivity.context).manager.allClear()) {
                        testManager.setLevel(((MenuActivity)MenuActivity.context).manager.getLevel() + 1);
                        testManager.reset();

                        Manager result2Manager = connect_put.changeUserInfo(testManager);
                        ((MenuActivity)MenuActivity.context).manager = ((MenuActivity)MenuActivity.context).getManager(); // Manager 객체 내용 업데이트

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TestResultActivity.this, "테스트", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder builder = new AlertDialog.Builder(TestResultActivity.this);
                                builder.setTitle("레벨 업!");
                                builder.setMessage("축하합니다!!");
                                builder.setIcon(android.R.drawable.ic_dialog_info);

                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(TestResultActivity.this, "레벨 업", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });
                    }
                } else
                    System.out.println(false);
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

        checkAdapter adapter = new checkAdapter(); // 어댑터 객체 생성
        adapter.setItems(result); // 어댑터 아이템 설정

        recyclerView.setAdapter(adapter); // 어댑터 등록

    }

}
