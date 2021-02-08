package com.test.vocaup.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.vocaup.DO.ListAll;
import com.test.vocaup.DO.Problem;
import com.test.vocaup.R;

import java.util.ArrayList;

public class checkAdapter extends RecyclerView.Adapter<checkAdapter.ViewHolder> {
    private ArrayList<Problem> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.wrong_item_list, parent, false);

        return new ViewHolder(itemView);
    }





    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Problem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // 뷰 홀더
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView problem_word;
        private TextView textView_answer;
        private TextView textView_check;
        private TextView check;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            problem_word = itemView.findViewById(R.id.problem_word);
            textView_answer = itemView.findViewById(R.id.textView_answer);
            textView_check = itemView.findViewById(R.id.textView_check);
            check = itemView.findViewById(R.id.check);
        }

        public void setItem(Problem problem) {
            int answer=problem.getAnswer();
            int choice=problem.getChoice();
            problem_word.setText(problem.getShow());
            textView_answer.setText(problem.getSelect(answer));
            textView_check.setText(problem.getSelect(choice));
            if(answer==choice)
                check.setText("O");
            else
                check.setText("X");
        }
    }

    public void addItem(Problem problem) {
        items.add(problem);
    }

    // 어댑터 아이템 설정
    public void setItems(ArrayList<Problem> items) {
        this.items = items;
    }

    public Problem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Problem problem) {
        items.set(position, problem);
    }
}
