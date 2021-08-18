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
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.Tripapp.Data.Alarm;
import com.example.Tripapp.services.AlarmService;

import java.util.Calendar;
import java.util.Random;

public class RingActivity extends AppCompatActivity {

    Button cancel;
    Button snooze;
    Button start;
    ImageView clock;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    final int notificationID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);

        initComponent();

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
            }
        });

        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.MINUTE, 1);

                Alarm alarm = new Alarm(
                        new Random().nextInt(Integer.MAX_VALUE),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR),
                        System.currentTimeMillis(),
                        true,
                        "Snooze"
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
                builder.setContentTitle("alarmTitle");
                builder.setContentText("Ring Ring .. Ring Ring");
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

            }
        });
        mediaPlayer.start();

        long[] pattern = {0, 100, 1000};
        vibrator.vibrate(pattern, 0);

        animateClock();
    }

    private void initComponent() {
        cancel = findViewById(R.id.activity_ring_dismiss);
        snooze = findViewById(R.id.activity_ring_snooze);
        start = findViewById(R.id.activity_ring_start);
        clock = findViewById(R.id.activity_ring_clock);

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
