package com.example.Tripapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.Tripapp.R;
import com.example.Tripapp.Trip;
import com.example.Tripapp.trip_map.TripMapActivity;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UpcomingTripsFrag extends Fragment {
    private ListView upcomingListView;
    private ArrayList<Trip> trips;
    UpcomingViewModel upcomingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        trips = new ArrayList<Trip>();
        upcomingViewModel =
                new ViewModelProvider(getActivity()).get(UpcomingViewModel.class);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.US);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd /MM /yyyy", Locale.US);
        trips.add(new Trip("Trip Name",
                new Trip.Place("The Start Point", new LatLng(30.5,30.5)),
                new Trip.Place("The Trip End Point", new LatLng(31.5,31.5)),
                dateFormat.format(calendar.getTime()), timeFormat.format(calendar.getTime()), calendar));
        trips.add(new Trip("Aswan Trip",
                new Trip.Place("Cairo", new LatLng(30.5,30.5)),
                new Trip.Place("Aswan", new LatLng(31.5,31.5)),
                dateFormat.format(calendar.getTime()), timeFormat.format(calendar.getTime()), calendar));
        trips.add(new Trip("university",
                new Trip.Place("Cairo", new LatLng(30.5,30.5)),
                new Trip.Place("Zagazig", new LatLng(31.5,31.5)),
                dateFormat.format(calendar.getTime()), timeFormat.format(calendar.getTime()), calendar));
        upcomingViewModel.getTrip().observe(getViewLifecycleOwner(), new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                trips.add(0, trip);
            }
        });

        upcomingListView = view.findViewById(R.id.upcomin_frag_listview);
        upcomingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TripMapActivity.class);
                startActivity(intent);
            }
        });
        UpcomingTripAdapter simpleArrayAdapter = new UpcomingTripAdapter(view.getContext(), trips);
        upcomingListView.setAdapter(simpleArrayAdapter);
        return view;
    }

}