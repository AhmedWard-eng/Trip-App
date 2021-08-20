package com.example.Tripapp.Notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Tripapp.MainActivity;
import com.example.Tripapp.R;
import com.example.Tripapp.Trip;
import com.example.Tripapp.TripAppDataActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.Tripapp.TripAppDataActivity.TRIP_TITLE;

public class AddNotes extends AppCompatActivity {
    static ArrayList<String> notes;
    Button btnAddNotes;
    NoteAdapter noteAdapter;
    public static DatabaseReference reference = null;
    ListView listView;
    Trip trip;
    String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        notes = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            String s = intent.getStringExtra(TripAppDataActivity.TRIP_ID);
            if (s != null) {
                trip = new Trip();
                trip.setTitle(intent.getStringExtra(TRIP_TITLE));
                trip.setTripId(intent.getStringExtra(TripAppDataActivity.TRIP_ID));
                trip.setAlarmId(intent.getIntExtra(TripAppDataActivity.ALARM_ID, 0));
                trip.setDateText(intent.getStringExtra(TripAppDataActivity.TRIP_DATE));
                trip.setTimeText(intent.getStringExtra(TripAppDataActivity.TRIP_TIME));
                trip.setStartPoint(intent.getStringExtra(TripAppDataActivity.TRIP_START_POINT));
                trip.setEndPoint(intent.getStringExtra(TripAppDataActivity.TRIP_END_POINT));
                id = intent.getStringExtra(TripAppDataActivity.NOTES);
//                trip.setNotes(note);
                trip.setRound(intent.getBooleanExtra(TripAppDataActivity.IS_ROUND, false));

                /////////////////////////////////////////////////////////////////////////////
                trip.setMonth(intent.getIntExtra(TripAppDataActivity.MONTH, 0));
                trip.setDay(intent.getIntExtra(TripAppDataActivity.DAY, 0));
                trip.setYear(intent.getIntExtra(TripAppDataActivity.YEAR, 0));
                trip.setHour(intent.getIntExtra(TripAppDataActivity.HOUR, 0));
                trip.setMinute(intent.getIntExtra(TripAppDataActivity.MINUTE, 0));
            }
        }

        btnAddNotes = findViewById(R.id.add_notes);
        listView = findViewById(R.id.list_notes);

        FirebaseDatabase data = FirebaseDatabase.getInstance();
        reference = data.getReference("Notes");

        noteAdapter = new NoteAdapter(AddNotes.this, notes);
        listView.setAdapter(noteAdapter);


        btnAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadNotes(notes);
            }
        });
    }

    public void uploadNotes(ArrayList<String> notes) {
        if(id == null){
            id = reference.push().getKey();
            Toast.makeText(this,id,Toast.LENGTH_LONG).show();
        }

//        Toast.makeText(this,id,Toast.LENGTH_LONG).show();
        reference.child(id).setValue(notes);
        trip.setNotes(id);
        MainActivity.databaseRefUpcoming.child(trip.getTripId()).setValue(trip);
    }

    @Override
    protected void onStart() {
        super.onStart();

        reference.child(id).child("").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    String nota = (String) datasnapshot.getValue();
                    notes.add(nota);
                }
                noteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {

            }
        });
        if (notes.isEmpty()) {
            notes.add("");
        }
    }
}