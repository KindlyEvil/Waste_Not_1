package com.example.waste_not;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText fulName,Email,password,conf_password,address,phoneNo,organisation;
    Button btnReg;
    RadioButton ngoUser,hUser;
    String typeUser="";
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    String userID;


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("SignUp!!!");

        //Background Animation
        LinearLayout linearLayout=findViewById(R.id.sign_lay);
        AnimationDrawable animationDrawable=(AnimationDrawable)linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        //Casting views
        fulName =findViewById(R.id.full_name);
        Email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.pass);
        conf_password = findViewById(R.id.conf_pass);
        address = findViewById(R.id.add);
        phoneNo = findViewById(R.id.phone_no);
        btnReg = findViewById(R.id.btn_reg);
        ngoUser = findViewById(R.id.ngo_user);
        hUser = findViewById(R.id.h_user);
        progressBar = findViewById(R.id.progress);
        organisation = findViewById(R.id.orgName);

        //Database Instance
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Cannot", "registration error");

                final String fullName = fulName.getText().toString();
                final String email = Email.getText().toString();
                String pas = password.getText().toString();
                String confPass = conf_password.getText().toString();
                final String add = address.getText().toString();
                final String phone = phoneNo.getText().toString();
                final String organisationName = organisation.getText().toString();
                if (TextUtils.isEmpty(fullName)) {
                    fulName.setError("Name is Required...");
                    return;
                } else if (TextUtils.isEmpty(organisationName)) {
                    organisation.setError("Organisation Name is Required...");
                    return;
                } else if (TextUtils.isEmpty(email)) {
                    Email.setError("Email is Required...");
                    return;
                } else if (TextUtils.isEmpty(pas)) {
                    password.setError("Password is Required...");
                    return;
                } else if (TextUtils.isEmpty(confPass)) {
                    conf_password.setError("Confirm Password cannot be empty ...");
                    return;
                } else if (TextUtils.isEmpty(add)) {
                    address.setError("Address Details cannot be empty...");
                    return;
                } else if (TextUtils.isEmpty(phone)) {
                    phoneNo.setError("Phone Number is Required...");
                    return;
                }

                if (pas.contentEquals(confPass)) {


                    if (ngoUser.isChecked()) {
                        typeUser = "NGO";
                        progressBar.setVisibility(View.VISIBLE);

                        //Creating User
                        firebaseAuth.createUserWithEmailAndPassword(email, pas)
                                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "createUserWithEmail:success");
                                            FirebaseUser user = firebaseAuth.getCurrentUser();

                                            userID = firebaseAuth.getCurrentUser().getUid();
                                            DocumentReference documentReference = fStore.collection("Ngo User").document(userID);
                                            Map<String, Object> user1 = new HashMap<>();
                                            user1.put("Full_Name", fullName);
                                            user1.put("Organisation Name", organisationName);
                                            user1.put("Email", email);
                                            user1.put("Address", add);
                                            user1.put("PhoneNumb", phone);
                                            user1.put("UserType", typeUser);

                                            documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "onSuccess: " + userID);
                                                    Toast.makeText(SignUp.this, "Account Successfully Created!!!!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            finish();

                                            startActivity(new Intent(getApplicationContext(), user_type.class));


                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(SignUp.this, "Authentication failed."+task.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);


                                        }

                                    }
                                });
                    }
                    if (hUser.isChecked()) {
                        typeUser = "Hostel/Hotel";
                        progressBar.setVisibility(View.VISIBLE);

                        //Creating User
                        firebaseAuth.createUserWithEmailAndPassword(email, pas)
                                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "createUserWithEmail:success");
                                            FirebaseUser user = firebaseAuth.getCurrentUser();

                                            userID = firebaseAuth.getCurrentUser().getUid();
                                            DocumentReference documentReference = fStore.collection("Hostel_Hotel User").document(userID);
                                            Map<String, Object> user1 = new HashMap<>();
                                            user1.put("Full_Name", fullName);
                                            user1.put("Organisation Name", organisationName);
                                            user1.put("Email", email);
                                            user1.put("Address", add);
                                            user1.put("PhoneNumb", phone);
                                            user1.put("UserType", typeUser);
                                            documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "onSuccess: " + userID);
                                                    Toast.makeText(SignUp.this, "Account Successfully Created!!!!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            finish();

                                            startActivity(new Intent(getApplicationContext(), user_type.class));


                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(SignUp.this, "Authentication failed."+task.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }

                                    }
                                });
                    }

                }
                else{
                    Toast.makeText(SignUp.this, "Confirm Password does not match!!!", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

}