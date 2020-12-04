package com.example.fakenewsdetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistrationActivity extends AppCompatActivity {

    private EditText eName;
    private EditText eEmail;
    private EditText ePassword;
    private EditText ePasswordCheck;
    private Button eSignup;
    private FirebaseAuth mAuth;
    private LinearLayout eSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        eName = findViewById(R.id.regName);
        eEmail = findViewById(R.id.regEmail);
        ePassword = findViewById(R.id.regPassword);
        ePasswordCheck = findViewById(R.id.regPasswordCheck);
        eSignup = findViewById(R.id.regSignup);
        eSignin = findViewById(R.id.regSignin);
        eSignin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });


        eSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String inputName = eName.getText().toString();
                final String inputEmail =  eEmail.getText().toString();
                String inputPassword =  ePassword.getText().toString();
                String inputPassword1 =  ePasswordCheck.getText().toString();
                if( !validateString(inputName) || !validateString(inputEmail) || !validateString(inputPassword) || !validateString(inputPassword1)){
                    Toast.makeText(getApplicationContext(),"Please fill all the Blanks!",Toast.LENGTH_SHORT).show();
                }
                else if(!validatePassword(inputPassword ,inputPassword1)){
                    Toast.makeText(getApplicationContext(),"Password doesn't match!",Toast.LENGTH_SHORT).show();
                }
                else {
//                      sharedEditor.putString("Email",inputEmail);
//                      sharedEditor.putString("Password",inputPassword);
//                      sharedEditor.apply();
                    mAuth.createUserWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//
                                final FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegistrationActivity.this, "Registration successful!! Verification email sent to " + user.getEmail() ,Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));


                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(inputName)
                                                    .build();

                                            user.updateProfile(profileUpdates)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d("Profile", "User profile updated.");
                                                            }
                                                            else{
                                                                Log.d("Profile", task.getException().getMessage());
                                                            }
                                                        }
                                                    });

                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                            user.delete()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d("delete", "User account deleted.");
                                                            }
                                                        }
                                                    });

                                        }
                                    }
                                });

                            }
                            else{
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }

            }
        });
    }
    public boolean validateString(String s){
        return !s.isEmpty();
    }
    public boolean validatePassword(String p1, String p2){
        return p1.equals(p2);
    }
}