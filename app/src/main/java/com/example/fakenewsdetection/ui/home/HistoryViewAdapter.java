package com.example.fakenewsdetection.ui.home;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakenewsdetection.R;

import java.util.ArrayList;


public class HistoryViewAdapter extends RecyclerView.Adapter<HistoryViewAdapter.MyViewHolder> {
    public ArrayList<ModelHistory> itemArrayList;
    public HistoryViewAdapter.ItemListener itemListener;

    public HistoryViewAdapter(ArrayList<ModelHistory> itemArrayList, HistoryViewAdapter.ItemListener itemListener ) {
        this.itemListener = itemListener;
        this.itemArrayList = itemArrayList;
    }
    @NonNull
    @Override
    public HistoryViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v =  (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_history_row, parent, false);
        return new HistoryViewAdapter.MyViewHolder(v,itemListener);

    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewAdapter.MyViewHolder holder, final int position) {

        ModelHistory model = itemArrayList.get(position);
        holder.link.setText(model.getLink());
        holder.datetime.setText(model.getDate());
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        ImageButton button;
        TextView link;
        TextView datetime;
        HistoryViewAdapter.ItemListener itemListener;

        public MyViewHolder(View view, final HistoryViewAdapter.ItemListener itemListener) {

            super(view);
            link =  view.findViewById(R.id.historyLink);
            button = view.findViewById(R.id.historyDelete);
            datetime = view.findViewById(R.id.historyDate);
            this.itemListener = itemListener;
            view.setOnClickListener(this);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    itemListener.onItemRemoveClick(itemArrayList.get(pos).getId());
                    itemArrayList.remove(pos);
                    notifyItemRemoved(pos);
                }
            });

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            itemListener.onItemClick(itemArrayList.get(pos).getLink());
        }


    }

    public interface ItemListener{
        void onItemClick(String link);
        void onItemRemoveClick(Integer id);

    }
}
