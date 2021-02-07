package com.test.vocaup.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.vocaup.DO.ListAll;
import com.test.vocaup.R;
import com.test.vocaup.server.Connect_get;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpellingMean extends AppCompatActivity {
    private JSONObject result = new JSONObject();
    TextView spelling;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_mean);
        spelling = findViewById(R.id.spelling);
        button1 = findViewById(R.id.button);

        Intent intent = getIntent();

        //Toast.makeText(this, intent.getStringExtra("Token"), Toast.LENGTH_SHORT).show();

        Thread thread = new Thread() {
            @Override
            public void run() {
                result = new Connect_get(intent.getStringExtra("Token"))
                        .problem_get("problem/spelling_mean", "10");
            }
        };

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    spelling.setText((String)result.getString("problem_type"));
                    System.out.println((String)result.getString("problem_type"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}