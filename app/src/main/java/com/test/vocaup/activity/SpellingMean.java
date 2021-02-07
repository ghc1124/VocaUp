package com.test.vocaup.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.test.vocaup.R;
import com.test.vocaup.server.Connect_get;

import org.json.JSONException;
import org.json.JSONObject;

public class SpellingMean extends AppCompatActivity {
    private JSONObject result = new JSONObject();
    TextView spelling;
    Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_mean);
        spelling =  (TextView)findViewById(R.id.spelling);
        button1 = (Button)findViewById(R.id.button);
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = new Connect_get().problem_get("problem/spelling_mean", "10");
                
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