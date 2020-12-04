package com.example.fakenewsdetection.ui.home;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fakenewsdetection.R;

import java.util.ArrayList;

public class TrendingFakeNewsViewAdapter extends RecyclerView.Adapter<TrendingFakeNewsViewAdapter.MyViewHolder> {

    public ArrayList<ModelNews> itemArrayList;
    public ItemListener itemListener;
    public TrendingFakeNewsViewAdapter(ArrayList<ModelNews> itemArrayList, ItemListener itemListener ) {
        this.itemListener = itemListener;
        this.itemArrayList = itemArrayList;
    }
    @NonNull
    @Override
    public TrendingFakeNewsViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_trending_row, parent, false);
        return new MyViewHolder(v,itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingFakeNewsViewAdapter.MyViewHolder holder, int position) {
        ModelNews item = itemArrayList.get(position);
        holder.textView.setText(item.getName());
        holder.imageView.setImageDrawable((Drawable)item.getIconPath());
        holder.link.setText((String)item.getLink());

    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        public TextView textView;
        public ImageView imageView;
        public TextView link;
        ItemListener itemListener;
        public MyViewHolder(View view, ItemListener itemListener) {
            super(view);
            textView = view.findViewById(R.id.newsTitle);
            imageView = view.findViewById(R.id.newsImage);
            link = view.findViewById(R.id.newsLink);
            this.itemListener = itemListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.onItemClick((String) link.getText());
        }

    }
    public interface ItemListener{
        void onItemClick(String hey);
    }
}
