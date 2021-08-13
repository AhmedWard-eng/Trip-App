package com.example.Tripapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class TripAppDataActivity extends AppCompatActivity {
    public static final String TRIP_POSITION = "Trip Position";
    public static final String TRIP_TITLE = "sending the object";
    public static final String TRIP_UNIQUE_ID = "UniqueId";
    public static final String TRIP_DATE = "Trip date";
    public static final String TRIP_TIME = "Trip time";
    public static final String TRIP_START_POINT = "Start Point";
    public static final String TRIP_END_POINT = "End Point";
    public static final String TRIP_SET_TIME = "Trip Set Time";

    FloatingActionButton btn_add;
    Button btn_notes;
    TextView txt_notes, view_notes, txt_date, txt_time;
    EditText txt_title;
    SearchView txt_StartPoint, txt_endPoint;
    Spinner txt_repeat, txt_kind;
    Calendar calendarDate;
    Calendar calendarTime;
    Calendar theSetTime;
    String date;
    String time;
    int position;


    int i = 0;
    ArrayList<String> notes = new ArrayList<>();
    DatePickerDialog.OnDateSetListener onDateSetListener;

    int year, month, day, hours, minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_app_data);



        intiComponent();

        getEditData();
        position = -1;

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trip trip = saveData();
                Intent intentToMainActivity = new Intent(TripAppDataActivity.this, MainActivity.class);
                intentToMainActivity.putExtra(TRIP_TITLE, trip.getTitle());
                intentToMainActivity.putExtra(TRIP_UNIQUE_ID, "from_TripDataActivity");
                intentToMainActivity.putExtra(TRIP_DATE, trip.getDateText());
                intentToMainActivity.putExtra(TRIP_TIME, trip.getTimeText());
                intentToMainActivity.putExtra(TRIP_START_POINT, trip.getStartPoint());
                intentToMainActivity.putExtra(TRIP_END_POINT, trip.getEndPoint());
                intentToMainActivity.putExtra(TRIP_SET_TIME, trip.getTheSetTime());
                intentToMainActivity.putExtra(TRIP_POSITION,position);
                TripAppDataActivity.this.startActivity(intentToMainActivity);
            }
        });

        btn_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.add(txt_notes.getText().toString());
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


        txt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime();
            }
        });
    }

    private void getEditData() {
        Intent intent = getIntent();
        if (intent != null) {
            Trip trip = new Trip();
            String s = intent.getStringExtra(TripAppDataActivity.TRIP_UNIQUE_ID);

            Log.i("receiving_from_intent", String.valueOf(intent.getStringExtra(TripAppDataActivity.TRIP_UNIQUE_ID)));
            if (s != null) {
                txt_title.setText(intent.getStringExtra(TRIP_TITLE));
                txt_StartPoint.setQuery(intent.getStringExtra(TRIP_START_POINT), false);
                txt_endPoint.setQuery(intent.getStringExtra(TRIP_END_POINT), false);
                theSetTime = (Calendar) intent.getSerializableExtra(TRIP_SET_TIME);
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.US);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd /MM /yyyy", Locale.US);
                txt_date.setText(dateFormat.format(theSetTime.getTime()));
                txt_time.setText(timeFormat.format(theSetTime.getTime()));
                position = intent.getIntExtra(TRIP_POSITION,0);

            }
        }
    }

    private void showTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendarTime = Calendar.getInstance();
                calendarTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarTime.set(Calendar.MINUTE, minute);
                calendarTime.setTimeZone(TimeZone.getDefault());
                SimpleDateFormat format = new SimpleDateFormat("hh:mm aa", Locale.US);
                time = format.format(calendarTime.getTime());
                txt_time.setText(time);
            }
        }, hours, minutes, false);
        timePickerDialog.show();
    }

    private void showDate() {
        onDateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            date = dayOfMonth + "/" + month + "/" + year;
            txt_date.setText(date);

            calendarDate = Calendar.getInstance();
            calendarDate.set(year, month, dayOfMonth);

        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener, year, month, day);
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
        if (theSetTime == null) {
            theSetTime = Calendar.getInstance();
            theSetTime.set(Calendar.HOUR, calendarTime.get(Calendar.HOUR));
            theSetTime.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE));
            theSetTime.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE));
            theSetTime.set(Calendar.AM_PM, calendarTime.get(Calendar.AM_PM));
            theSetTime.set(Calendar.DAY_OF_MONTH, calendarDate.get(Calendar.MINUTE));
            theSetTime.set(Calendar.MONTH, calendarDate.get(Calendar.MONTH));
            theSetTime.set(Calendar.YEAR, calendarDate.get(Calendar.YEAR));
        }
        data.setTheSetTime(theSetTime);
        data.setTitle(txt_title.getText().toString());
        data.setDateText(txt_date.getText().toString());
        data.setTimeText(txt_time.getText().toString());
        data.setRound("Round".equals(String.valueOf(txt_repeat.getSelectedItem())));
        data.setRepetition(String.valueOf(txt_kind.getSelectedItem()));
        data.setStartPoint(String.valueOf(txt_StartPoint.getQuery()));
        data.setEndPoint(String.valueOf(txt_endPoint.getQuery()));
        data.setNotes(notes);
        return data;

    }

}