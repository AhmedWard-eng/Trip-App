package com.example.Tripapp;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class app extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
       // FirebaseDatabase.getInstance().getReference("Trip_Data").keepSynced(true);

    }
}
