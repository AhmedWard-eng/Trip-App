package com.example.Tripapp.Notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.Tripapp.R;
import com.example.Tripapp.Trip;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Add_Notes extends AppCompatActivity {
    static ArrayList<String> notes ;
    Button add_notes;
    static ArrayList<String> note ;
    NoteAdapter noteAdapter ;
    public static DatabaseReference reference = null;
    int i;
    EditText Note ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        add_notes = findViewById(R.id.add_notes);
        Note = findViewById(R.id.note) ;

        FirebaseDatabase data = FirebaseDatabase.getInstance();
        reference = data.getReference("Notes");

        notes = new ArrayList<String>();
        notes.add("");

        note = new ArrayList() ;
        noteAdapter = new NoteAdapter(Add_Notes.this,notes);
        ListView listView = findViewById(R.id.list_notes);
        listView.setAdapter(new NoteAdapter(this, notes ));

        add_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadNotes(notes);
            }
        });
    }

    public void uploadNotes(ArrayList<String> notes){
        String id = reference.push().getKey();
        reference.child(id).setValue(notes);
    }

}