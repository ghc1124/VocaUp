package com.test.vocaup.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.vocaup.DO.ListAll;
import com.test.vocaup.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<ListAll> items = new ArrayList<>();
    private String flag;

    private OnItemClick onItemClick;

    public ListAdapter(OnItemClick onItemClick, String flag) {
        this.onItemClick = onItemClick;
        this.flag = flag;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }

    public interface OnItemClick {
        void onClick(int index);
    }

    private OnItemClickListener itemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListAll item = items.get(position);
        holder.setItem(item);

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(item.isSelected());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    onItemClick.onClick(item.getIndex());
                } else {
                    onItemClick.onClick(-1 * item.getIndex());
                    if(flag.equals("userWord")) {
                        items.remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                    }
                }

                item.setSelected(b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // 뷰 홀더
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_word;
        private TextView textView_part;
        private TextView textView_mean;
        private CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_word = itemView.findViewById(R.id.textView_word);
            textView_part = itemView.findViewById(R.id.textView_part);
            textView_mean = itemView.findViewById(R.id.textView_mean);
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(itemClickListener != null) {
                            itemClickListener.onItemClick(view, pos);
                        }
                    }
                }
            });*/
            checkBox = itemView.findViewById(R.id.checkBox);
        }

        public void setItem(ListAll listAll) {
            textView_word.setText(listAll.getWord());
            textView_part.setText(listAll.getPart());
            textView_mean.setText(listAll.getMean());
        }
    }

    public void addItem(ListAll listAll) {
        items.add(listAll);
    }

    // 어댑터 아이템 설정
    public void setItems(ArrayList<ListAll> items) {
        this.items = items;
    }

    public ListAll getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, ListAll listAll) {
        items.set(position, listAll);
    }
}
