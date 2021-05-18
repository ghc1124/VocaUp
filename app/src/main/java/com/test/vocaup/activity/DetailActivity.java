package com.test.vocaup.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.test.vocaup.DO.Manager;
import com.test.vocaup.R;

public class DetailActivity extends AppCompatActivity {
    public static final String PREF = "Save";

    private String name;
    private String profile;
    private String email;
    private String token;

    private TextView textView_name;
    private ImageView image_profile2;
    private TextView textView_level;

    private TextView textView_blank_spelling;
    private TextView textView_mean_spelling;
    private TextView textView_spelling_mean;
    private TextView textView_spelling_mean_link;
    private TextView textView_spelling_sort;
    private TextView textView_pron_mean;
    private TextView textView_recap;

    private Button btn_logout;

    private Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        profile = intent.getStringExtra("profile");
        email = intent.getStringExtra("email");
        token = intent.getStringExtra("token");
        manager = (Manager)intent.getSerializableExtra("Manager");

        if(savedInstanceState != null) {
            name = savedInstanceState.getString("name");
            profile = savedInstanceState.getString("profile");
            email = savedInstanceState.getString("email");
            token = intent.getStringExtra("token");
        }

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textView_name = (TextView)findViewById(R.id.textView_name);
        image_profile2 = (ImageView)findViewById(R.id.image_profile2);
        textView_level = findViewById(R.id.textView_level);

        textView_name.setText(name);
        textView_level.setText("lv. " + manager.getLevel());

        textView_blank_spelling = findViewById(R.id.textView_blank_spelling);
        textView_mean_spelling = findViewById(R.id.textView_mean_spelling);
        textView_spelling_mean = findViewById(R.id.textView_spelling_mean);
        textView_spelling_mean_link = findViewById(R.id.textView_spelling_mean_link);
        textView_spelling_sort = findViewById(R.id.textView_spelling_sort);
        textView_pron_mean = findViewById(R.id.textView_pron_mean);
        textView_recap = findViewById(R.id.textView_recap);

        textView_blank_spelling.setText(manager.getBlank_spelling() > 0 ? "통과" : "시험 필요");
        textView_mean_spelling.setText(manager.getMean_spelling() > 0 ? "통과" : "시험 필요");
        textView_spelling_mean.setText(manager.getSpelling_mean() > 0 ? "통과" : "시험 필요");
        textView_spelling_mean_link.setText(manager.getSpelling_mean_link() > 0 ? "통과" : "시험 필요");
        textView_spelling_sort.setText(manager.getSpelling_sort() > 0 ? "통과" : "시험 필요");
        textView_pron_mean.setText(manager.getPron_mean() > 0 ? "통과" : "시험 필요");
        textView_recap.setText(manager.getRecap() == 5 ? "통과" : manager.getRecap() + "회");

        Glide.with(this).load(profile).into(image_profile2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause()");
        SharedPreferences preferences = getSharedPreferences(PREF, 0);
        SharedPreferences.Editor editor = preferences.edit();
        if(name != null && profile != null & email != null) {
            editor.putString("name", name);
            editor.putString("profile", profile);
            editor.putString("email", email);
            editor.putString("token", token);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = getSharedPreferences(PREF, 0);
        if((preferences != null) && (preferences.contains("name"))) {
            name = preferences.getString("name", "");
            profile = preferences.getString("profile", "");
            email = preferences.getString("email", "");
            token = preferences.getString("token", "");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(name != null && profile != null & email != null) {
            outState.putString("name", name);
            outState.putString("profile", profile);
            outState.putString("email", email);
            outState.putString("token", token);
        }
    }
}