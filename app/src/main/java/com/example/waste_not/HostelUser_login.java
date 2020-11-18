package com.example.waste_not;

import android.content.Intent;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HostelUser_login extends AppCompatActivity {
    EditText mEmail,mPassword;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_h);
        getSupportActionBar().setTitle("Hostel/Hotel User LogIn");

        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.hostel_lay);
        AnimationDrawable animationDrawable=(AnimationDrawable)linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
    }

    public void Hostel_d(View view) {

        //Creating Variables
        mEmail = findViewById(R.id.txt_Email);
        mPassword = findViewById(R.id.pass_text);
        progressBar = findViewById(R.id.prog);

        //Initializing FirebaseAuth
        fAuth = FirebaseAuth.getInstance();

        String email = mEmail.getText().toString();
        String pass = mPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Enter Email...");
            return;
        }else if (TextUtils.isEmpty(pass)) {
            mPassword.setError("Password is Required...");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(HostelUser_login.this, "Log In Successfully!!!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),hostel_details.class));
                }else {
                    Toast.makeText(HostelUser_login.this, "ERROR!!!"+task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }

            }
        });


    }
}


