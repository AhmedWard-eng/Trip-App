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
        trips.add(new Trip("Trip", "start Point", "end point", 29.924526, 31.205753,
                dateFormat.format(calendar.getTime()),
                timeFormat.format(calendar.getTime()),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
        trips.add(new Trip("Trip", "start Point", "end point", 29.924526, 31.205753,
                dateFormat.format(calendar.getTime()),
                timeFormat.format(calendar.getTime()),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
        trips.add(new Trip("Trip", "start Point", "end point", 29.924526, 31.205753,
                dateFormat.format(calendar.getTime()),
                timeFormat.format(calendar.getTime()),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
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


}