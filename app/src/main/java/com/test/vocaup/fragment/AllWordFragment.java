package com.test.vocaup.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_allword, container, false);

        RecyclerView recyclerView = viewGroup.findViewById(R.id.recycler_word);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ListAdapter adapter = new ListAdapter();

        adapter.setItems(result);

        recyclerView.setAdapter(adapter);

        return viewGroup;
    }
}