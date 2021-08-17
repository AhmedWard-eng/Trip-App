package com.example.Tripapp.Data;

import com.google.android.gms.maps.model.LatLng;

public class MYPlace {
    String name;
    LatLng latLng;

    public MYPlace(String name) {
        this.name = name;
    }

    public MYPlace(String name, LatLng latLng) {
        this.name = name;
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng name) {
        this.latLng = latLng;
    }
}

