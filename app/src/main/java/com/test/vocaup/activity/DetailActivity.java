package com.test.vocaup.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.vocaup.R;

public class DetailActivity extends AppCompatActivity {

    public static final String PREF = "Save";

    private String name;
    private String profile;
    private String email;
    private String token;

    private TextView textView_name;
    private ImageView image_profile2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        profile = intent.getStringExtra("profile");
        email = intent.getStringExtra("email");
        token = intent.getStringExtra("token");

        if(savedInstanceState != null) {
            name = savedInstanceState.getString("name");
            profile = savedInstanceState.getString("profile");
            email = savedInstanceState.getString("email");
            token = intent.getStringExtra("token");
        }

        textView_name = (TextView)findViewById(R.id.textView_name);
        image_profile2 = (ImageView)findViewById(R.id.image_profile2);

        textView_name.setText(name);
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