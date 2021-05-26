package com.test.vocaup.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.vocaup.DO.ListAll;
import com.test.vocaup.R;
import com.test.vocaup.activity.MenuActivity;
import com.test.vocaup.adapter.ListAdapter;
import com.test.vocaup.server.Connect_delete;
import com.test.vocaup.server.Connect_get;
import com.test.vocaup.server.Connect_put;

import java.util.ArrayList;

public class MyWordListOnceFragment extends Fragment implements MenuActivity.OnBackPressedListener, ListAdapter.OnItemClick {
    private ArrayList<ListAll> result = new ArrayList<>();

    public static MyWordListOnceFragment newInstance() { // 모든 프래그먼트에 공통으로 들어가야될 부분!!
        return new MyWordListOnceFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = new Connect_get(((MenuActivity)MenuActivity.context).manager.getToken())
                        .UserWordget("userWordList");
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

        ListAdapter adapter = new ListAdapter(this, "userWord"); // 어댑터 객체 생성
        adapter.setItems(result); // 어댑터 아이템 설정

        adapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Toast.makeText(getActivity(), "" + pos, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter); // 어댑터 등록

        return viewGroup;
    }

    @Override
    public void onBack() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("").setMessage("뒤로 가시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                // MainFragment 로 교체
                ((MenuActivity) getActivity()).replaceFragment(MyWordListFragment.newInstance());
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // Fragment 호출 시 반드시 호출되는 오버라이드 메소드
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Other", "onAttach()");
        ((MenuActivity)context).setOnBackPressedListener(this);
    }

    @Override
    public void onClick(int index) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                if(index > 0) {
                    new Connect_put(((MenuActivity)MenuActivity.context).manager.getToken()).appendUserWord(index);
                } else {
                    new Connect_delete(((MenuActivity)MenuActivity.context).manager.getToken()).removeUserWord(-1 * index);
                }
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
