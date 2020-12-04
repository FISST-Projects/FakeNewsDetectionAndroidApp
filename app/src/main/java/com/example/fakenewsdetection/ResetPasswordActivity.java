package com.example.fakenewsdetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText resetEmail;
    private Button resetPassowrd;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mAuth = FirebaseAuth.getInstance();
        resetEmail  = findViewById(R.id.resetEmail);
        resetPassowrd = findViewById(R.id.resetpswdbtn);
        resetPassowrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = resetEmail.getText().toString();
                if(email.isEmpty()){
                    resetEmail.setError("Please enter Email id");
                    resetEmail.requestFocus();
                }
                else{
                    String userEmail="";
                    if(mAuth.getCurrentUser()!=null){
                        FirebaseUser user = mAuth.getCurrentUser();
                        userEmail = user.getEmail();

                    }
                    if(mAuth.getCurrentUser()==null || userEmail.compareTo(email)==0){
                        mAuth.sendPasswordResetEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            if(mAuth.getCurrentUser()!=null){
                                                mAuth.signOut();
                                            }
                                            Log.d("activity_reset_password", "Email sent.");
                                            startActivity(new Intent(ResetPasswordActivity.this,MainActivity.class));
                                            Toast.makeText(ResetPasswordActivity.this,"Reset Password Email sent to "+ email, Toast.LENGTH_SHORT).show();

                                        }
                                        else{
                                            Log.d("activity_reset_password", "Unsuccessful attempt to send Email.");
                                            Toast.makeText(ResetPasswordActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }
                    else{
                        Toast.makeText(ResetPasswordActivity.this,"It doesn't match with the user EmailId", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });
    }
}