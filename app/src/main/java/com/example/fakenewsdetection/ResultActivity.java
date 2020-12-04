package com.example.fakenewsdetection;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    CardView label,news,score,title,link;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:  onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        score = findViewById(R.id.resultScore);
        news = findViewById(R.id.resultNews);
        label = findViewById(R.id.resultLabel);
        link = findViewById(R.id.resultLink);
        title = findViewById(R.id.resultTitle);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorTitleBar), PorterDuff.Mode.SRC_ATOP);
        }
        TextView tv;
        Intent intent = getIntent();
        if(intent.getStringExtra("news")!=null){
            tv  = (TextView) news.getChildAt(0);
            tv.setText(intent.getStringExtra("news"));
        }
        if(intent.getStringExtra("label")!=null){
            label.setVisibility(View.VISIBLE);
           tv  = (TextView) label.getChildAt(0);
            tv.setText(intent.getStringExtra("label"));
        }
        if(intent.getStringExtra("title")!=null){
            title.setVisibility(View.VISIBLE);
            tv  = (TextView) title.getChildAt(0);
            tv.setText(intent.getStringExtra("title"));
        }
        if(intent.getStringExtra("score")!=null){
            score.setVisibility(View.VISIBLE);
            tv  = (TextView) score.getChildAt(0);
            tv.setText(intent.getStringExtra("score"));
        }
        if(intent.getStringExtra("link")!=null){
            link.setVisibility(View.VISIBLE);
            tv = (TextView) link.getChildAt(0);
            tv.setText(intent.getStringExtra("link"));
        }



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}