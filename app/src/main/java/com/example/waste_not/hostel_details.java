package com.example.waste_not;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
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

public class hostel_details extends AppCompatActivity {
    TextView fullName,email,orgName,address,phoneNumber;
    String userId;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hostel_details);
        getSupportActionBar().setTitle("HOSTEL/HOTEL USER");
        ConstraintLayout constraintLayout = findViewById(R.id.hostel_det);
        AnimationDrawable animationDrawable = (AnimationDrawable)constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email_h);
        orgName = findViewById(R.id.Organisation_h);
        address =findViewById(R.id.Ad);
        phoneNumber = findViewById(R.id.number);
        userId = fAuth.getCurrentUser().getUid();


        final DocumentReference documentReference= fStore.collection("Hostel_Hotel User").document(userId);
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
                    Toast.makeText(hostel_details.this, "No Details Found!!"+documentSnapshot.getMetadata(), Toast.LENGTH_SHORT).show();
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
        alert.setMessage("You are a Hostel/Hotel User." +
                " If you want to donate food then go to the next page where you have to fill some detail" +
                " and press then Donate Button." +
                " After that your request will be available for NGOs to accept the food " +
                "and then Ngo will contact you on the contact details provided by you.");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.create().show();

    }

    public void btnDonate(View view) {
        startActivity(new Intent(getApplicationContext(),donation_details.class));
    }
}
