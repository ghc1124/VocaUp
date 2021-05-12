package com.test.vocaup.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.test.vocaup.R;
import com.test.vocaup.activity.MenuActivity;

public class MyWordListFragment extends Fragment implements MenuActivity.OnBackPressedListener {

    public static MyWordListFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new MyWordListFragment();
    }

    private Button btn_singly; //하나씩 보기 버튼
    private Button btn_once; // 한번에 보기 버튼

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_mywordlist, container, false);

        btn_singly = viewGroup.findViewById(R.id.btn_singly); // 하나씩 보기 버튼 할당
        btn_singly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MenuActivity) getActivity()).replaceFragment(MyWordListSinglyFragment.newInstance());
            }
        });


        btn_once = viewGroup.findViewById(R.id.btn_once); // 한번에 보기 버튼 할당
        btn_once.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MenuActivity) getActivity()).replaceFragment(MyWordListOnceFragment.newInstance());
            }
        });

        return viewGroup;
    }

    @Override
    public void onBack() {
        // Log.e("Other", "onBack()");
        // 리스너를 설정하기 위해 Activity 를 받아옴
        MenuActivity activity = (MenuActivity)getActivity();
        // 한번 뒤로가기 버튼을 눌렀다면 Listener 를 null로 설정
        activity.setOnBackPressedListener(null);
        // MainFragment 로 교체
        ((MenuActivity) getActivity()).replaceFragment(MenuFragment.newInstance());
    }

    // Fragment 호출 시 반드시 호출되는 오버라이드 메소드
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Log.e("Other", "onAttach()");
        ((MenuActivity)context).setOnBackPressedListener(this);
    }
}