package com.example.Tripapp.ui.history;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.Tripapp.MainActivity;
import com.example.Tripapp.R;
import com.example.Tripapp.Trip;
import com.example.Tripapp.trip_map.MapsFragment;
import com.example.Tripapp.ui.createAcount.MainActivity2;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {
    private ArrayList<Trip> trips;
    ListView lstView;
    BestAdapter bestAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        trips = new ArrayList<>();
        lstView = view.findViewById(R.id.best_list);
        bestAdapter = new BestAdapter(getActivity(), trips);
        lstView.setAdapter(bestAdapter);

//        MarkerOptions startMarker = new MarkerOptions()
////                .icon(BitmapDescriptorFactory.defaultMarker(0))
//                .position(new LatLng(29.924526, 31.205753))
//                .title("trip.getTitle()" + " - " +" trip.getStartPoint()");
//        MapsFragment.tripMap.addMarker(startMarker);


    }




    public void onStart() {
        super.onStart();
        MainActivity.databaseRefHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                trips.clear();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    Trip trip = datasnapshot.getValue(Trip.class);
                    trips.add(trip);
                }
                bestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        int c = 0;
        for(Trip trip : trips){
            c++;
            if(c > 360) { c = 0; }

            MarkerOptions startMarker = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(c))
                    .position(new LatLng(trip.getStartLongitude(), trip.getStartLatitude()))
                    .title(trip.getTitle() + " - " + trip.getStartPoint());
            MapsFragment.tripMap.addMarker(startMarker);

            MarkerOptions endMarker = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(c))
                    .position(new LatLng(trip.getEndLongitude(), trip.getEndLatitude()))
                    .title(trip.getTitle() + " - " + trip.getEndPoint());
            MapsFragment.tripMap.addMarker(endMarker);
        }
    }
}

