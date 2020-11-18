package com.example.waste_not;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class user_type extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_type);
        getSupportActionBar().setTitle("User Type");

        //Background Animation
        LinearLayout linearLayout=findViewById(R.id.userType_lay);
        AnimationDrawable animationDrawable=(AnimationDrawable)linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
    }

    public void Ngo_login(View view) {
        startActivity(new Intent(getApplicationContext(),LogIn.class));
    }

    public void Hotel_login(View view) {
        startActivity(new Intent(getApplicationContext(),HostelUser_login.class));
    }

    public void Register(View view) {
        startActivity(new Intent(getApplicationContext(),SignUp.class));
    }
}
