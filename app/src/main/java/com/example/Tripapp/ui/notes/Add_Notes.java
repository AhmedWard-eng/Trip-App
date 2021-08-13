package com.example.Tripapp.ui.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.Tripapp.R;
import java.util.ArrayList;


public class Add_Notes extends Fragment {
    static ArrayList<String> notes ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_add_notes, container, false);
       return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notes = new ArrayList<String>();
                notes.add("Empty");

        ListView listView = view.findViewById(R.id.list_notes);
        listView.setAdapter(new NoteAdapter(getActivity(), notes ));

    }
}