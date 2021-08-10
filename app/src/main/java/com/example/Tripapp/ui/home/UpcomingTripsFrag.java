package com.example.Tripapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.Tripapp.MainActivity;
import com.example.Tripapp.R;

import java.util.ArrayList;

public class UpcomingTripsFrag extends Fragment {
    ListView upcomingListView;
    private ArrayList<UpcomingTripData> upcomingTripData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        upcomingTripData = new ArrayList<UpcomingTripData>();
        upcomingTripData.add(new UpcomingTripData("Trip Name", "The Start Point", "The Trip End Point", "26-10-2021", "00:00"));
        upcomingTripData.add(new UpcomingTripData("Aswan Trip", "Cairo", "Aswan", "20-08-2021", "00:00"));
        upcomingTripData.add(new UpcomingTripData("university", "Cairo", "Zagazig", "21-09-2021", "08:00"));



        upcomingListView = view.findViewById(R.id.upcomin_frag_listview);
        UpcomingTripAdapter simpleArrayAdapter = new UpcomingTripAdapter(view.getContext(), upcomingTripData);
        upcomingListView.setAdapter(simpleArrayAdapter);

        

        return view;
    }
}