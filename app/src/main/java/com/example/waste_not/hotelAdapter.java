package com.example.waste_not;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class hotelAdapter extends FirestoreRecyclerAdapter<food,hotelAdapter.hotelHolder> {

    public hotelAdapter(@NonNull FirestoreRecyclerOptions<food> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull hotelHolder holder, int position, @NonNull food model) {
        holder.titleH.setText(model.getOrganisationName());
        holder.cont.setText(model.getContactInfo());
        holder.des.setText(model.getFoodDescription());

    }

    @NonNull
    @Override
    public hotelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_list,parent,false);
        return new hotelHolder(v);
    }

    //Deleting Data
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class hotelHolder extends RecyclerView.ViewHolder{

        TextView titleH;
        TextView des;
        TextView cont;

        public hotelHolder(@NonNull View itemView) {
            super(itemView);
            titleH = itemView.findViewById(R.id.hotelName);
            des = itemView.findViewById(R.id.foodDes);
            cont = itemView.findViewById(R.id.contact);
        }
    }
}
