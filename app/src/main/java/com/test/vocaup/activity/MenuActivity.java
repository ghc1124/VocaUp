package com.test.vocaup.activity;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.test.vocaup.DO.*;
import com.test.vocaup.fragment.MenuFragment;
import com.test.vocaup.R;

import java.util.Stack;

public class MenuActivity extends AppCompatActivity {

    public static final String PREF = "Save";

    private Toolbar toolbar;
    private TextView text_name;
    private ImageView image_profile;

    private String name;
    private String profile;
    private String email;
    private String token;

    private Fragment menu_fragment;

    public Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        text_name = (TextView)findViewById(R.id.text_name);
        image_profile = (ImageView)findViewById(R.id.image_profile);

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

        text_name.setText(name + " 님");
        Glide.with(this).load(profile).into(image_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        menu_fragment = new MenuFragment();

        replaceFragment(menu_fragment);

        image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_detail = new Intent(getApplicationContext(), DetailActivity.class);
                intent_detail.putExtra("token", token);
                intent_detail.putExtra("name", name);
                intent_detail.putExtra("profile", profile); // getPhotoUrl이 URI 형태
                intent_detail.putExtra("email", email);
                startActivity(intent_detail);
            }
        });

        Thread thread = new Thread() {
            @Override
            public void run() {
                // 사용자 정보 등록 or 받아오기
                UserManager userManager = new UserManager();
                manager = userManager.getUserInfo(new Manager(token));
            }
        };

        thread.start();

        try {
            thread.join();
        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println(manager.getLevel());
    }

    // 프래그먼트 전환하는 메소드. "fragment"에 이름 지정해주면 됨.
    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
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

    // 뒤로가기 버튼 입력시간이 담길 long 객체
    private long pressedTime = 0;

    // 리스너 생성
    public interface OnBackPressedListener {
        public void onBack();
    }

    // 리스너 객체 생성
    private OnBackPressedListener mBackListener;

    // 리스너 설정 메소드
    public void setOnBackPressedListener(OnBackPressedListener listener) {
        mBackListener = listener;
    }

    // 뒤로가기 버튼을 눌렀을 때의 오버라이드 메소드
    @Override
    public void onBackPressed() {
        // 다른 Fragment 에서 리스너를 설정했을 때 처리
        if(mBackListener != null) {
            mBackListener.onBack();
            Log.e("!!!", "Listener is not null");
            // 리스너가 설정되지 않은 상태(ex)MenuFragment)라면
            // 뒤로가기 버튼을 연속적으로 두번 눌렀을 때 앱이 종료
        } else {
            Log.e("!!!", "Listener is null");
            if ( pressedTime == 0 ) {
                Toast.makeText(this,"한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
                pressedTime = System.currentTimeMillis();
            }
            else {
                int seconds = (int) (System.currentTimeMillis() - pressedTime);

                if ( seconds > 2000 ) {
                    Toast.makeText(this,"한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
                    pressedTime = 0 ;
                }
                else {
                    super.onBackPressed();
                    Log.e("!!!", "onBackPressed : finish, killProcess");
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        }
    }

}