package com.example.Tripapp.Notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Tripapp.R;
import com.example.Tripapp.Trip;
import com.example.Tripapp.TripAppDataActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class ShowAllNotes extends AppCompatActivity {

    private ListView showListView;
    private ArrayList<Trip> trips = null;
    DatabaseReference reference = null;
    TextView txt_note;
    String string;
    ArrayList<String> notes;
    String[] arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_notes);
        notes = new ArrayList<>();
        showListView = findViewById(R.id.show_note_list_view);
        Intent intent = getIntent();
        string = intent.getStringExtra(TripAppDataActivity.NOTES);
        txt_note = findViewById(R.id.show_note);

        if (string == null) {
            notes.add(string);
        } else {
            arr = string.split("--");
            notes = new ArrayList<>(Arrays.asList(arr));
        }
        ShowNoteAdapter simpleArrayAdapter = new ShowNoteAdapter(this, notes);
        showListView.setAdapter(simpleArrayAdapter);

//        FirebaseDatabase data = FirebaseDatabase.getInstance();
//        reference = data.getReference("Notes");
    }

//    @Override
//    public void onStart() {
//        super.onStart();
////        reference.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
////
////                for ( DataSnapshot datasnapshot: snapshot.getChildren()) {
////                    Trip trip = datasnapshot.getValue(Trip.class);
////                    trips.add(trip);
////                }
////
////            }
////
////            @Override
////            public void onCancelled(@NonNull @NotNull DatabaseError error) {
////
////            }
////        });
//    }
}