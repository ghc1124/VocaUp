package com.test.vocaup.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.test.vocaup.DO.ListAll;
import com.test.vocaup.R;
import com.test.vocaup.server.Connect_get;

import java.util.ArrayList;

public class SpellingMean extends AppCompatActivity {
    private ArrayList<ListAll> result = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_mean);

        Thread thread = new Thread() {
            @Override
            public void run() {
                result = new Connect_get().get("problem/spelling_mean", "10");
            }
        };

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}