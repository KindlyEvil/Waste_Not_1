package com.example.waste_not;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class donation_details extends AppCompatActivity {

    EditText hotelN,description,contact;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_details);
        getSupportActionBar().setTitle("Foods To Donate");

        LinearLayout linearLayout=findViewById(R.id.donationLay);
        AnimationDrawable animationDrawable=(AnimationDrawable)linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        //Casting Views
        hotelN = findViewById(R.id.hotel_name);
        description = findViewById(R.id.food_description);
        contact = findViewById(R.id.contact_details);
        progressBar = findViewById(R.id.prog);


    }

    public void donate(View view) {
        //Initializing

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //startActivity(new Intent(getApplicationContext(),user_type.class));
        String hotelName = hotelN.getText().toString();
        String foodDes = description.getText().toString();
        String contactDetails = contact.getText().toString();


        if (TextUtils.isEmpty(hotelName)) {
            hotelN.setError("Organisation Name is Required...");
            return;
        } else if (TextUtils.isEmpty(foodDes)) {
            description.setError("Food Details");
            return;
        } else if (TextUtils.isEmpty(contactDetails)) {
            contact.setError("Contact Details Are Mandatory");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //Storing Data


        //FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("Available_Hotels_Hostel").document(userID);
                            Map<String, Object> user1 = new HashMap<>();
                            user1.put("OrganisationName", hotelName );
                            user1.put("contactInfo", contactDetails);
                            user1.put("foodDescription", foodDes);

                            documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess: " + userID);
                                    Toast.makeText(donation_details.this, "THANK YOU FOR DONATING!!!!!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(donation_details.this, "Error!!!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                            finish();

                            //startActivity(new Intent(getApplicationContext(), user_type.class));


                        }

}