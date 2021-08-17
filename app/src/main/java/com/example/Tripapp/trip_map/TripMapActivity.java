package com.example.Tripapp.trip_map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.Tripapp.R;

import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class TripMapActivity extends AppCompatActivity implements TaskLoadedCallback {
    private Polyline currentPolyline;
    static MarkerOptions place1 = new MarkerOptions().position(new LatLng(30.7255, 31.5710)).title("Location 1");
    static MarkerOptions place2 = new MarkerOptions().position(new LatLng(30.0225, 31.3014)).title("Location 2");
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_map);

        btnStart = findViewById(R.id.btn_barTripMap_start);
        btnStart.setOnClickListener(view -> {
            MapsFragment.tripMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(30.7255, 31.5710)));
            MapsFragment.tripMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.7255, 31.5710), 8f));
            //new FetchURL(TripMapActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
        });
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = MapsFragment.tripMap.addPolyline((PolylineOptions) values[0]);
    }

}