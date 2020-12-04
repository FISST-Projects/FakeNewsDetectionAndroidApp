package com.example.fakenewsdetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText eEmail;
    private EditText ePassword;
    private Button eLogin;
    private LinearLayout eSignup;
    private TextView forgotPswd;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser!=null &&  currentUser.isEmailVerified()) {
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                }
            }
        };

        eEmail = findViewById(R.id.email);
        ePassword = findViewById(R.id.password);
        eLogin = findViewById(R.id.login);
        eSignup = findViewById(R.id.signup);
        forgotPswd = findViewById(R.id.forgotpswd);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        eSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });

        forgotPswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ResetPasswordActivity.class));
            }
        });

        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputEmail = eEmail.getText().toString();
                String inputPassword = ePassword.getText().toString();
                if(inputEmail.isEmpty()){
                    eEmail.setError("Please enter Email id");
                    eEmail.requestFocus();
                }
                else if(inputPassword.isEmpty()){
                    ePassword.setError("Please enter Password");
                    ePassword.requestFocus();
                }
                else{
                    spinner.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(inputEmail,inputPassword)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        final FirebaseUser user = mAuth.getCurrentUser();
                                        if(!user.isEmailVerified()){
                                            spinner.setVisibility(View.GONE);
                                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(MainActivity.this, "Please verify to Login. Verification email sent to " + user.getEmail() ,
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                    else{
                                                        Toast.makeText(getApplicationContext(),"Please verify to Login. Verification mail has already been sent.",Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }
                                        else{
                                            spinner.setVisibility(View.GONE);
                                            Log.i("Login","Successful");
                                            Toast.makeText(MainActivity.this, "Login successful " + user.getDisplayName() ,Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
//
                                        }
//

                                    }
                                    else{
                                        spinner.setVisibility(View.GONE);
                                        Log.e("Signin Error", "onCancelled", task.getException());
                                        Toast.makeText(MainActivity.this, task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
//
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
}