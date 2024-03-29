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

import com.example.Tripapp.Data.Alarm;
import com.example.Tripapp.MainActivity;
import com.example.Tripapp.R;
import com.example.Tripapp.Trip;

import com.example.Tripapp.services.RescheduleAlarmsService;
import com.example.Tripapp.TripAppDataActivity;


import com.example.Tripapp.ui.createAcount.MainActivity2;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UpcomingTripsFrag extends Fragment {
    private ListView upcomingListView;
    private ArrayList<Trip> trips = null;


    FloatingActionButton fabStartTripDataActivity;

    DatabaseReference reference = null;
    UpcomingTripAdapter simpleArrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fabStartTripDataActivity = view.findViewById(R.id.fab);
        fabStartTripDataActivity.setOnClickListener(mView -> {
            Snackbar.make(mView, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Intent intent = new Intent(getContext(), TripAppDataActivity.class);
            startActivity(intent);
        });


        trips = new ArrayList<Trip>();

        FirebaseDatabase data = FirebaseDatabase.getInstance();
//        reference = data.getReference("Trip_Data");


        upcomingListView = view.findViewById(R.id.upcomin_frag_listview);
//        upcomingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getContext(), TripMapActivity.class);
//                startActivity(intent);
//            }
//        });
        simpleArrayAdapter = new UpcomingTripAdapter(view.getContext(), trips);
        upcomingListView.setAdapter(simpleArrayAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity.databaseRefUpcoming.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                trips.clear();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    Trip trip = datasnapshot.getValue(Trip.class);
                    trips.add(trip);
//                    if (trip != null) {
////                        Alarm alarm = new Alarm(trip.getTripId(), trip.getAlarmId(), trip.getHour(), trip.getMinute(), trip.getDay(), trip.getMonth(), trip.getYear(), 0, true, trip.getTitle());
////                        alarm.schedule(getContext());
//                    }
                }
                simpleArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {

            }
        });
        for (Trip trip : trips) {
            Alarm alarm = new Alarm(trip.getTripId(), trip.getAlarmId(), trip.getHour(), trip.getMinute(), trip.getDay(), trip.getMonth(), trip.getYear(), 0, true, trip.getTitle());
            alarm.schedule(getContext());
        }
    }
}
