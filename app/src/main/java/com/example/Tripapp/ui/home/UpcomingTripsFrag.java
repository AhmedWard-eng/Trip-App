package com.example.Tripapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.Tripapp.R;
import com.example.Tripapp.Trip;

import java.util.ArrayList;
import java.util.Date;

public class UpcomingTripsFrag extends Fragment {
    ListView upcomingListView;
    private ArrayList<Trip> trips;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        trips = new ArrayList<Trip>();
        trips.add(new Trip("Trip Name", "The Start Point", "The Trip End Point", new Date(), new Date()));
        trips.add(new Trip("Aswan Trip", "Cairo", "Aswan",  new Date(),  new Date()));
        trips.add(new Trip("university", "Cairo", "Zagazig",  new Date(),  new Date()));



        upcomingListView = view.findViewById(R.id.upcomin_frag_listview);
        UpcomingTripAdapter simpleArrayAdapter = new UpcomingTripAdapter(view.getContext(), trips);
        upcomingListView.setAdapter(simpleArrayAdapter);

        

        return view;
    }
}