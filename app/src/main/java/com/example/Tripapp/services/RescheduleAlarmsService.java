package com.example.Tripapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

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