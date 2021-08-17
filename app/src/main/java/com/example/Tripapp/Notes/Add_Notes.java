package com.example.Tripapp.Notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.Tripapp.R;

import java.util.ArrayList;

public class Add_Notes extends AppCompatActivity {
    static ArrayList<String> notes ;
    Button add_notes ;
    static ArrayList<String> note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        add_notes = findViewById(R.id.add_notes);

        notes = new ArrayList<String>();
        notes.add("");

        ListView listView = findViewById(R.id.list_notes);
        listView.setAdapter(new NoteAdapter(this, notes ));

        note = new ArrayList<>();
        add_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}