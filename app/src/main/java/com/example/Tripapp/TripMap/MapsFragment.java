package com.example.Tripapp.TripMap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.Tripapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsFragment extends Fragment
{
    static GoogleMap tripMap;

    private OnMapReadyCallback callback = new OnMapReadyCallback()
    {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap)
        {
            tripMap = googleMap;
            //LatLng sydney = new LatLng(-34, 151);
            //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            /*googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            googleMap.clear(); //clear old markers

            CameraPosition googlePlex = CameraPosition.builder()
                    .target(new LatLng(37.4219999,-122.0862462))
                    .zoom(10)
                    .bearing(0)
                    .tilt(45)
                    .build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(37.4219999, -122.0862462))
                    .title("Spider Man")
                    .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.ic_launcher_foreground)));

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(37.4629101,-122.2449094))
                    .title("Iron Man")
                    .snippet("His Talent : Plenty of money"));

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(37.3092293,-122.1136845))
                    .title("Captain America"));*/
            Log.d("mylog", "Added Markers");
            tripMap.addMarker(TripMap.place1);
            tripMap.addMarker(TripMap.place2);

            CameraPosition googlePlex = CameraPosition.builder()
                    .target(new LatLng(30.7406,31.5764))
                    .zoom(7)
                    .bearing(0)
                    .tilt(45)
                    .build();

            tripMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null);

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}