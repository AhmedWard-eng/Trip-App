package com.example.Tripapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class TripAppDataActivity extends AppCompatActivity {
    FloatingActionButton btn_add;
    Button btn_notes;
    TextView txt_notes, view_notes, txt_date, txt_time;
    EditText txt_title;
    SearchView txt_StartPoint, txt_endPoint;
    Spinner txt_repeat, txt_kind;
    Calendar calendarDate;
    Calendar calendarTime;

    int i = 0;
    ArrayList<String> notes = new ArrayList<>();
    DatePickerDialog.OnDateSetListener date;

    int year, month, day, hours, minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_app_data);


        intiComponent();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trip trip = saveData();
                Intent intentToMainActivity = new Intent(TripAppDataActivity.this,MainActivity.class);
                intentToMainActivity.putExtra("Trip",trip);
                startActivity(intentToMainActivity);
            }
        });

        btn_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.set(i, txt_notes.getText().toString());
                view_notes.append(txt_notes.getText() + "\n");
                txt_notes.setText(" ");
                i++;
            }
        });

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

//        Calendar calendartime = Calendar.getInstance();
//        hours = calendartime.get(Calendar.HOUR_OF_DAY);
//        mins = calendartime.get(Calendar.MINUTE);

        txt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime();
            }
        });
    }

    private void showTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendarTime = Calendar.getInstance();
                calendarTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarTime.set(Calendar.MINUTE, minute);
                calendarTime.setTimeZone(TimeZone.getDefault());
                SimpleDateFormat format = new SimpleDateFormat("kk:mm aa");
                String time = format.format(calendarTime.getTime());
                txt_time.setText(time);
            }
        }, hours, minutes, false);
        timePickerDialog.show();
    }

    private void showDate() {
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                txt_date.setText(date);

                calendarDate = Calendar.getInstance();
                calendarDate.set(year, month, dayOfMonth);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, date, year, month, day);
        datePickerDialog.show();
    }


    private void intiComponent() {
        btn_add = findViewById(R.id.add);
        btn_notes = findViewById(R.id.Notes);
        txt_date = findViewById(R.id.due_date);
        txt_notes = findViewById(R.id.txt_notes);
        txt_time = findViewById(R.id.set_time);
        txt_title = findViewById(R.id.title);
        txt_StartPoint = findViewById(R.id.your_location);
        txt_endPoint = findViewById(R.id.your_distenation);
        txt_repeat = findViewById(R.id.repeatation);
        txt_kind = findViewById(R.id.kind_of_trip);
        view_notes = findViewById(R.id.notes);

    }

    private Trip saveData() {
        Trip data = new Trip();
        data.setTitle(txt_title.getText().toString());
        data.setDate(calendarDate.getTime());
        data.setTime(calendarTime.getTime());
        data.setRound("Round" == String.valueOf(txt_repeat.getSelectedItem()));
        data.setRepetition(String.valueOf(txt_kind.getSelectedItem()));
        data.setStartPoint(String.valueOf(txt_StartPoint.getTextAlignment()));
        data.setEndPoint(String.valueOf(txt_endPoint.getTextAlignment()));
        data.setNotes(notes);
        return data;

    }

}