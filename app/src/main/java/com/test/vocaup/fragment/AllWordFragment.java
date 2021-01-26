package com.test.vocaup.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.vocaup.DO.ListAll;
import com.test.vocaup.R;
import com.test.vocaup.adapter.ListAdapter;
import com.test.vocaup.server.Connect_get;

import java.util.ArrayList;

public class AllWordFragment extends Fragment {
    private ArrayList<ListAll> result = new ArrayList<>();

    public static AllWordFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new AllWordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = new Connect_get().get("list", "1");
            }
        };

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 프래그먼트가 위치한 ViewGroup
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_allword, container, false);

        // ViewGroup 내에서 View 찾음
        RecyclerView recyclerView = viewGroup.findViewById(R.id.recycler_word);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // 세로방향으로 레이아웃 매니저 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false
        );

        recyclerView.setLayoutManager(linearLayoutManager); // 레이아웃 매니저 등록

        ListAdapter adapter = new ListAdapter(); // 어댑터 객체 생성
        adapter.setItems(result); // 어댑터 아이템 설정

        recyclerView.setAdapter(adapter); // 어댑터 등록

        return viewGroup;
    }
}