package com.example.Tripapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.Tripapp.Data.Alarm;
import com.example.Tripapp.MainActivity;
import com.example.Tripapp.Trip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class RescheduleAlarmsService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);


        //TODO: looping in the data in the firebase

//        AlarmRepository alarmRepository = new AlarmRepository(getApplication());
//
//        alarmRepository.getAlarmsLiveData().observe(this, new Observer<List<Alarm>>() {
//            @Override
//            public void onChanged(List<Alarm> alarms) {
//                for (Alarm a : alarms) {
//                    if (a.isStarted()) {
//                        a.schedule(getApplicationContext());
//                    }
//                }
//            }
//        });
        MainActivity.databaseRefUpcoming.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for ( DataSnapshot datasnapshot: snapshot.getChildren()) {
                    Trip trip = datasnapshot.getValue(Trip.class);
                    Alarm alarm = new Alarm(trip.getTripId(),trip.getAlarmId(),trip.getHour(),trip.getMinute(),trip.getDay(),trip.getMonth(),trip.getYear(),0,true,trip.getTitle());
                    alarm.schedule(RescheduleAlarmsService.this);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {

            }
        });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}