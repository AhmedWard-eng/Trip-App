package com.example.Tripapp.Data;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.example.Tripapp.BroadCast.AlarmBroadCastReceiver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Alarm {


    private int alarmId;
    private String tripId;

    private int hour, minute, day, month, year;
    private boolean started;

    private long created;

    private String title;
    public static final String TRIP_ID = "FireBase Id";
    public static final String TRIP_TITLE = "trip title";

    public Alarm(String tripId,int alarmId, int hour, int minute, int day, int month, int year,long created, boolean started, String title) {
        this.alarmId = alarmId;
        this.tripId = tripId;
        this.hour = hour;
        this.minute = minute;
        this.started = started;
        this.day = day;
        this.month = month;
        this.year = year;
        this.created = created;
        this.title = title;
    }

    public Alarm(int alarmId) {
        this.alarmId = alarmId;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isStarted() {
        return started;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }



    public void schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmBroadCastReceiver.class);
        intent.putExtra(TRIP_ID,tripId);
        intent.putExtra(TRIP_TITLE,title);


        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
//        String date;
//        date = dateFormat.format(calendar.getTime());
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
        } else {



            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    alarmPendingIntent
            );
            this.started = true;
        }


    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadCastReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
        this.started = false;

//        String dateCancelFormat;
//        dateCancelFormat ="you canceled the trip " + title;
//        String toastText = String.format("Alarm cancelled for %02d:%02d with id %d", hour, minute, alarmId);
//        Toast.makeText(context, dateCancelFormat, Toast.LENGTH_SHORT).show();
//        Log.i("cancel", dateCancelFormat);
    }


    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
    public void acceptAlarm(Context context, Calendar calendar){

        DateFormat dateFormat = new SimpleDateFormat("hh:mm aa yy-MM-dd", Locale.US);
        String string = "your trip will start at: " + dateFormat.format(calendar.getTime());
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }
}
