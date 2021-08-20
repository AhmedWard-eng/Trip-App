package com.example.Tripapp.Notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Tripapp.R;
import com.example.Tripapp.Trip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class show_all_notes extends AppCompatActivity {

    private ListView showListView;
    private ArrayList<Trip> trips = null;
    DatabaseReference reference = null;
    TextView txt_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_notes);

        txt_note = findViewById(R.id.show_note);
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        reference = data.getReference("Notes");
    }

    @Override
    public void onStart() {
        super.onStart();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for ( DataSnapshot datasnapshot: snapshot.getChildren()) {
                    Trip trip = datasnapshot.getValue(Trip.class);
                    trips.add(trip);
                }
//                shownoteAdapter show = new shownoteAdapter(show_all_notes.this,Trip);
//                showListView.setAdapter(show);
//                show.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}