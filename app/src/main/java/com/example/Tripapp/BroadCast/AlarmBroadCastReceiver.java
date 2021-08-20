package com.example.Tripapp.BroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.example.Tripapp.Data.Alarm;
import com.example.Tripapp.services.AlarmService;
import com.example.Tripapp.services.RescheduleAlarmsService;

public class AlarmBroadCastReceiver extends BroadcastReceiver {

    private String title;
    private String id;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            startRescheduleAlarmsService(context);
        } else {
            startAlarmService(context, intent);
        }
    }

    private void startAlarmService(Context context, Intent intent) {
        title = intent.getStringExtra(Alarm.TRIP_TITLE);
        id = intent.getStringExtra(Alarm.TRIP_ID);
        Intent intentService = new Intent(context, AlarmService.class);
        intentService.putExtra(Alarm.TRIP_ID,id);
        intentService.putExtra(Alarm.TRIP_TITLE,title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }
    }

    private void startRescheduleAlarmsService(Context context) {
        Intent intentService = new Intent(context, RescheduleAlarmsService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }
    }
}