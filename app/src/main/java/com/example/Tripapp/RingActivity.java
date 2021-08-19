package com.example.Tripapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Tripapp.Data.Alarm;
import com.example.Tripapp.services.AlarmService;
import com.example.Tripapp.services.FloatingWidgetService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class RingActivity extends AppCompatActivity {

    Button cancel;
    Button snooze;
    Button start;
    ImageView clock;
    TextView textTitle;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    final int notificationID = 1;
    private String title;
    private String id;
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);

        initComponent();
        trip = getTrip();

        Intent intent = getIntent();
        title = intent.getStringExtra(Alarm.TRIP_TITLE);
        id = intent.getStringExtra(Alarm.TRIP_ID);
        textTitle.setText(title);


        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        AlarmService.closeRingTone();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                finish();
                NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(RingActivity.this);
                mNotificationManager.cancelAll();


                trip.setTripKind("Canceled");
                MainActivity.databaseRefHistory.child(trip.getTripId()).setValue(trip);
                MainActivity.databaseRefUpcoming.child(trip.getTripId()).removeValue();
            }
        });

        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.MINUTE, 1);

                Alarm alarm = new Alarm("",
                        new Random().nextInt(Integer.MAX_VALUE),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR),
                        System.currentTimeMillis(),
                        true,
                        "Snooze.. " + title
                );

                alarm.schedule(getApplicationContext());

                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);

                PendingIntent pendingIntent = PendingIntent.getActivity(RingActivity.this, 0, intentService, 0);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(AlarmService.CHANNEL_ID, "CHANNEL DISPLAY NAME", NotificationManager.IMPORTANCE_HIGH);
                    channel.setDescription("alarm has started");
                    NotificationManager nm = getSystemService(NotificationManager.class);
                    nm.createNotificationChannel(channel);
                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), AlarmService.CHANNEL_ID);
                builder.setContentTitle(title);
                builder.setContentText("let's Start Our Trip");
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                builder.setSmallIcon(R.drawable.ic_baseline_alarm_add_24);
                builder.setContentIntent(pendingIntent);
                builder.setOngoing(true);
                builder.setContentIntent(PendingIntent.getActivity(RingActivity.this, 1, new Intent(RingActivity.this, RingActivity.class), 0));

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(RingActivity.this);
                notificationManagerCompat.notify(notificationID, builder.build());
                finish();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final double longitude = trip.getLongitude();
                final double latitude = trip.getLatitude();

                Uri intentUri = Uri.parse("google.navigation:q=" + longitude + "," + latitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, intentUri);
                intent.setPackage("com.google.android.apps.maps");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(RingActivity.this, "There's no app that can respond. Please, Install Google Maps", Toast.LENGTH_SHORT).show();
                }

                if (MainActivity.drawOverAppsAllowed) {
                    Intent serviceIntent = new Intent(RingActivity.this, FloatingWidgetService.class);
                    serviceIntent.putExtra("notes", trip.getNotes());
                    startService(serviceIntent);
                }


            }
        });
        mediaPlayer.start();

        long[] pattern = {0, 100, 1000};
        vibrator.vibrate(pattern, 0);

        animateClock();
    }

    private Trip getTrip() {
        ArrayList<Trip> trips = new ArrayList<>();
        Toast.makeText(RingActivity.this,"Alarm......... Alarm .....",Toast.LENGTH_LONG).show();
        MainActivity.databaseRefUpcoming.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    Trip checkedTrip = datasnapshot.getValue(Trip.class);
                    trips.add(checkedTrip);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
            }
        });
        for(Trip n:trips){
            if(n.getTripId() == id){
                return n;
            }
        }
        return null;
    }

    private void initComponent() {
        cancel = findViewById(R.id.activity_ring_dismiss);
        snooze = findViewById(R.id.activity_ring_snooze);
        start = findViewById(R.id.activity_ring_start);
        clock = findViewById(R.id.activity_ring_clock);
        textTitle = findViewById(R.id.text_title);

    }

    private void animateClock() {
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(clock, "rotation", 0f, 20f, 0f, -20f, 0f);
        rotateAnimation.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimation.setDuration(800);
        rotateAnimation.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        vibrator.cancel();
    }
}
