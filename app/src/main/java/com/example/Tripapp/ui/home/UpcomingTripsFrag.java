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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UpcomingTripsFrag extends Fragment  {
    private ListView upcomingListView;
    private ArrayList<Trip> trips = null;
    UpcomingViewModel upcomingViewModel;
    DatabaseReference reference = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        trips = new ArrayList<Trip>();

        FirebaseDatabase data = FirebaseDatabase.getInstance();
        reference = data.getReference("Trip_Data");

        reference.keepSynced(true);

        upcomingViewModel = new ViewModelProvider(getActivity()).get(UpcomingViewModel.class);


        upcomingViewModel =
                new ViewModelProvider(getActivity()).get(UpcomingViewModel.class);

        upcomingViewModel.getTrip().observe(getViewLifecycleOwner(), new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                trips.add(0, trip);
            }
        });

        upcomingListView = view.findViewById(R.id.upcomin_frag_listview);
//        upcomingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getContext(), TripMapActivity.class);
//                startActivity(intent);
//            }
//        });
        UpcomingTripAdapter simpleArrayAdapter = new UpcomingTripAdapter(view.getContext(), trips);
        upcomingListView.setAdapter(simpleArrayAdapter);
        return view;
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
                UpcomingTripAdapter upcomingTripAdapter = new UpcomingTripAdapter(getView().getContext(), trips);
                upcomingListView.setAdapter(upcomingTripAdapter);
                upcomingTripAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
