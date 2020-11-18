
package com.example.waste_not;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class availableHotels extends AppCompatActivity {
    private FirebaseFirestore fStore;
    private hotelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_hotels);
        getSupportActionBar().setTitle("Available Hotels/Hostels");

        LinearLayout linearLayout=findViewById(R.id.availableHotels_lay);
        AnimationDrawable animationDrawable=(AnimationDrawable)linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = null;
        try {
            Query query = fStore.collection("Available_Hotels_Hostel");
            FirestoreRecyclerOptions<food> options = new FirestoreRecyclerOptions.Builder<food>()
                    .setQuery(query, food.class)
                    .build();

            adapter = new hotelAdapter(options);

            recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();

        }

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
                showDialogue();
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void showDialogue(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Important");
        alert.setMessage("Your requested has been accepted. Please contact to the organisation manager" +
                " for the food pick up on the contact details provided by the Organisation.");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();

            }
        });
        alert.create().show();

    }
}
