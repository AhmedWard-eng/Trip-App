package com.example.Tripapp.Notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.Tripapp.R;

import java.util.ArrayList;

public class show_all_notes extends AppCompatActivity {


    TextView txt_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_notes);

        txt_note = findViewById(R.id.show_note);

    }
}