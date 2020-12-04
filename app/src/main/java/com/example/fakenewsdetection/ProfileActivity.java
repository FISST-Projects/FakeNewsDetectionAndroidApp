package com.example.fakenewsdetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fakenewsdetection.ui.profile.ModelProfile;
import com.example.fakenewsdetection.ui.profile.RecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity  implements  RecyclerViewAdapter.ProfileListener{
    private RecyclerView recyclerView;
    private ArrayList<ModelProfile> profileArrayList;
    private TextView pname;
    private TextView pemail;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private PopupWindow popUp;
    private  LinearLayout layout;
    private ConstraintLayout profile;
    private  AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.setTitle("Profile");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        pname = findViewById(R.id.profileName);
        pemail = findViewById(R.id.profileEmail);
//        pname.setText(currentUser.getDisplayName());
        pemail.setText(currentUser.getEmail());
        layout = (LinearLayout) findViewById(R.id.profilebox);
        profile = findViewById(R.id.profilePage);
        setPrivacyPolicy();
        initRecyclerView();
        setDeleteAccount();


    }

    private  void initRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.profileRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        profileArrayList = new ArrayList<>();
        createListData();
        recyclerView.setAdapter(new RecyclerViewAdapter(profileArrayList,this));

    }
    private void setPrivacyPolicy(){
        LayoutInflater layoutInflater = (LayoutInflater) ProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup_privacy,null);
        popUp = new PopupWindow(customView,LinearLayout.LayoutParams.WRAP_CONTENT, 1200,true);
        popUp.setAnimationStyle(R.style.Animation);
        popUp.setElevation(20);
        popUp.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        popUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                profile.setAlpha(1);
            }
        });

    }

    private void setDeleteAccount(){
        builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Are you sure?");
        builder.setMessage("Do you definitely want to delete the account?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Profile Activity", "User account deleted");
                            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                            Toast.makeText(ProfileActivity.this, "User account deleted",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("Profile Activity", Objects.requireNonNull(task.getException().getMessage()));
                            Toast.makeText(ProfileActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        builder.setNegativeButton("Cancel",null);

    }

    private void createListData(){
        ModelProfile item = new ModelProfile(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_password_change,null), "Change Password");
        profileArrayList.add(item);
        item = new ModelProfile(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_logout,null), "Logout");
        profileArrayList.add(item);
        item = new ModelProfile(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_privacy_policy,null), "Privacy policy");
        profileArrayList.add(item);
        item = new ModelProfile(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_delete_account,null), "Delete My Account");
        profileArrayList.add(item);

    }

    @Override
    public void onItemClick(int position) {

        switch(position) {
            case 0:
                startActivity(new Intent(ProfileActivity.this, ResetPasswordActivity.class));
                break;
            case 1:
                mAuth.signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                break;
            case 2:
                popUp.showAtLocation(profile, Gravity.CENTER, 0, 0);
                profile.setAlpha(0.5f);

                break;
            case 3:
                builder.create().show();

        }

    }
}