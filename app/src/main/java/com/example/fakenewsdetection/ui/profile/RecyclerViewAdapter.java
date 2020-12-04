package com.example.fakenewsdetection.ui.profile;

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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    public ArrayList<ModelProfile> profileArrayList;
    public RecyclerViewAdapter.ProfileListener profileListener;
    public RecyclerViewAdapter(ArrayList<ModelProfile> profileArrayList, RecyclerViewAdapter.ProfileListener profileListener ) {
        this.profileListener = profileListener;
        this.profileArrayList = profileArrayList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_row, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(v,profileListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelProfile profile = profileArrayList.get(position);
        holder.textView.setText(profile.getName());
        holder.imageView.setImageDrawable((Drawable)profile.getIconPath());

    }

    @Override
    public int getItemCount() {
        return profileArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        public TextView textView;
        public ImageView imageView;
        RecyclerViewAdapter.ProfileListener profileListener;
        public MyViewHolder(View view, RecyclerViewAdapter.ProfileListener profileListener) {
            super(view);
            textView = view.findViewById(R.id.profileItemName);
            imageView = view.findViewById(R.id.profileIconImage);
            this.profileListener = profileListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            profileListener.onItemClick(getAdapterPosition());
        }

    }
    public interface ProfileListener{
        void onItemClick(int position);
    }
}
