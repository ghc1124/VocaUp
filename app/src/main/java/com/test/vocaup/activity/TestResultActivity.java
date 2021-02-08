package com.test.vocaup.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.vocaup.DO.Problem;
import com.test.vocaup.R;
import com.test.vocaup.adapter.checkAdapter;

import java.util.ArrayList;

public class TestResultActivity extends AppCompatActivity {
    private ArrayList<Problem> result = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testresult);

        Thread thread = new Thread() {
            @Override
            public void run() {
                result = (ArrayList<Problem>)getIntent().getExtras().get("test_result");
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
