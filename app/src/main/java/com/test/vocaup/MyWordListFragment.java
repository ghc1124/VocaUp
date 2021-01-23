package com.test.vocaup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyWordListFragment extends Fragment {

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

        btn_once = viewGroup.findViewById(R.id.btn_once); // 하나씩 보기 버튼 할당
        btn_once.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MenuActivity) getActivity()).replaceFragment(MyWordListOnceFragment.newInstance());
            }
        });

        return viewGroup;
    }


}