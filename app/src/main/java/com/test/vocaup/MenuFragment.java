package com.test.vocaup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class MenuFragment extends Fragment {

    private ImageButton imgBtn_myWord; // 나만의 단어장 이미지 버튼

    public static MenuFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new MenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_menu, container, false);

        imgBtn_myWord = viewGroup.findViewById(R.id.imgBtn_myWord); // 나만의 단어장 버튼 할당
        imgBtn_myWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MenuActivity)getActivity()).replaceFragment(MyWordListFragment.newInstance());
            }
        });

        return viewGroup;
    }
}