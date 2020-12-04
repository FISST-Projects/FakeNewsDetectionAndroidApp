package com.example.fakenewsdetection.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentManager;

import com.example.fakenewsdetection.R;
import com.example.fakenewsdetection.db.DBHelper;

import java.util.ArrayList;


public class HistoryFragment extends Fragment implements HistoryViewAdapter.ItemListener  {


    private RecyclerView recyclerView;
    private ArrayList<ModelHistory> itemArraylist;
    DBHelper db;
    private HistoryViewAdapter.ItemListener itemListener;

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         db = new DBHelper(getContext());
         itemListener = (HistoryViewAdapter.ItemListener) this;

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_history, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.historyRecyclerView);
        initRecyclerView();
        TextView clear = view.findViewById(R.id.clearHistory);
;
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAllHistory();
                itemArraylist.clear();
                itemArraylist.addAll(db.getAllHistory());
                recyclerView.setAdapter(new HistoryViewAdapter(itemArraylist,itemListener));

            }
        });

    }

    private void initRecyclerView(){
        if(recyclerView!=null){
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
            itemArraylist = new ArrayList<>();
            itemArraylist.addAll(db.getAllHistory());
            recyclerView.setAdapter(new HistoryViewAdapter(itemArraylist,itemListener));

        }

    }


    @Override
    public void onItemClick(String link) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);

    }

    @Override
    public void onItemRemoveClick(Integer id) {
        db.deleteHistoryById(id);

    }

}
