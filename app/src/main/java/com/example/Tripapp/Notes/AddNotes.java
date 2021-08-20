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

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.example.Tripapp.TripAppDataActivity.TRIP_TITLE;

public class AddNotes extends AppCompatActivity {
    static ArrayList<String> notes;
    Button btnAddNotes;
    NoteAdapter noteAdapter;
    ListView listView;
    Trip trip;
    String[] arr = new String[10];
    String string;

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
                string = intent.getStringExtra(TripAppDataActivity.NOTES);
                trip.setRound(intent.getBooleanExtra(TripAppDataActivity.IS_ROUND, false));

                /////////////////////////////////////////////////////////////////////////////
                trip.setMonth(intent.getIntExtra(TripAppDataActivity.MONTH, 0));
                trip.setDay(intent.getIntExtra(TripAppDataActivity.DAY, 0));
                trip.setYear(intent.getIntExtra(TripAppDataActivity.YEAR, 0));
                trip.setHour(intent.getIntExtra(TripAppDataActivity.HOUR, 0));
                trip.setMinute(intent.getIntExtra(TripAppDataActivity.MINUTE, 0));
            }
        }

        if (string == null) {
            notes.add("");
        } else {
            arr = string.split("--");
            if (arr.length <= 1) {
                string = "";
                notes.add("");
            }
            notes = new ArrayList<>(Arrays.asList(arr));
        }

        btnAddNotes = findViewById(R.id.add_notes);
        listView = findViewById(R.id.list_notes);

//        FirebaseDatabase data = FirebaseDatabase.getInstance();
//        reference = data.getReference("Notes");

        noteAdapter = new NoteAdapter(AddNotes.this, notes);
        listView.setAdapter(noteAdapter);


        btnAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadNotes(notes);
                Intent intent1 = new Intent(AddNotes.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }

    public void uploadNotes(ArrayList<String> notes) {
//        Toast.makeText(this, String.valueOf(notes.size()), Toast.LENGTH_LONG).show();

        string = "";
        for (int i = 0; i < notes.size(); i++) {
            string += notes.get(i) + "--";
        }
        Toast.makeText(this, "Notes saved", Toast.LENGTH_LONG).show();

        trip.setNotes(string);

        MainActivity.databaseRefUpcoming.child(trip.getTripId()).setValue(trip);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}