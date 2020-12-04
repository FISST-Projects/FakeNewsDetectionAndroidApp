package com.example.fakenewsdetection;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.fakenewsdetection.db.DBHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import androidx.appcompat.widget.Toolbar;
import com.example.fakenewsdetection.ui.home.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    final int PERMISSION_CODE = 1;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.profile:
                startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
                break;
            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(!checkPermissionFromDevice())  requestPermission();
        mAuth = FirebaseAuth.getInstance();
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference gsReference = storage.getReferenceFromUrl("gs://fake-news-detection-296619.appspot.com/Fake_News_Dataset.csv");
//        final long ONE_MEGABYTE = 1024 * 1024;
//        gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                String testV= null;
//                try {
//                    testV = new JSONObject(new String(bytes)).toString();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.d("csv",testV);
//                // Data for "images/island.jpg" is returns, use this as needed
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Log.d("exception", Objects.requireNonNull(exception.getMessage()));
//                // Handle any errors
//            }
//        });
//        StorageReference gsReference = storage.getReferenceFromUrl("gs://fake-news-detection-296619.appspot.com/Fake_News_Dataset.csv");

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, fragmentManager );
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_trending1);
        tabs.getTabAt(1).setIcon(R.drawable.ic_search);
        tabs.getTabAt(2).setIcon(R.drawable.ic_history4);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();

    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.INTERNET,
        },PERMISSION_CODE);
    }
    private boolean checkPermissionFromDevice(){
        int internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        return internet == PackageManager.PERMISSION_GRANTED;
    }
}