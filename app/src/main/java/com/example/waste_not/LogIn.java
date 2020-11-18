package com.example.waste_not;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {
    EditText mEmail,mPassword;
    FirebaseAuth fAuth;
    Button loginBtn;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().setTitle("NGO LogIn");

        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.login_nl);
        AnimationDrawable animationDrawable =(AnimationDrawable)linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();


        //Creating Variables
        mEmail = findViewById(R.id.email_logN);
        mPassword = findViewById(R.id.pass_n);
        loginBtn = findViewById(R.id.loginNGO);
        progressBar = findViewById(R.id.pro);

        //Initializing FirebaseAuth
        fAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                            Toast.makeText(LogIn.this, "Log In Successfully!!!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),ngo_d.class));
                        }else {
                            Toast.makeText(LogIn.this, "ERROR!!!"+task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                             progressBar.setVisibility(View.GONE);

                        }

                        }
                    });
            }
        });

    }

}