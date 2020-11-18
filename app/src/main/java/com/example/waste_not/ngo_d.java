package com.example.waste_not;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ngo_d extends AppCompatActivity {
    TextView fullName,email,orgName,address,phoneNumber;
    String userId;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ngo_details);
        getSupportActionBar().setTitle("NGO USER");

        ConstraintLayout constraintLayout= findViewById(R.id.ngo_layout);
        AnimationDrawable animationDrawable=(AnimationDrawable)constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fullName = findViewById(R.id.fullN);
        email = findViewById(R.id.Email);
        orgName = findViewById(R.id.Org);
        address =findViewById(R.id.Address);
        phoneNumber = findViewById(R.id.phoneNo);
        userId = fAuth.getCurrentUser().getUid();

        final DocumentReference documentReference= fStore.collection("Ngo User").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot.exists()){
                    fullName.setText(documentSnapshot.getString("Full_Name"));
                    email.setText(documentSnapshot.getString("Email"));
                    orgName.setText(documentSnapshot.getString("Organisation Name"));
                    address.setText(documentSnapshot.getString("Address"));
                    phoneNumber.setText(documentSnapshot.getString("PhoneNumb"));
                }else {
                    Toast.makeText(ngo_d.this, "No Details Found!!"+documentSnapshot.getMetadata(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.info:
                showDialogue();
                return true;
            case R.id.logout:
                finish();
                startActivity(new Intent(getApplicationContext(),user_type.class));
                Toast.makeText(this, "LogOut Successfully!!!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void showDialogue(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Info");
        alert.setMessage("You are an Ngo User." +
                " If you need food, search for the available hotel/hostel. " +
                "If hotel/hostels are available then first note down their contact details carefully " +
                "and then contact them for the pick up of food." +
                " By swapping you can accept the request for the available hotels/hostels.");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.create().show();

    }


    public void search(View view) {
        startActivity(new Intent(getApplicationContext(),availableHotels.class));
    }
}

