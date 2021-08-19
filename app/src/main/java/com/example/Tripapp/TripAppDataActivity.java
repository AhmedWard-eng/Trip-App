package com.example.Tripapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Tripapp.Data.Alarm;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TripAppDataActivity extends AppCompatActivity {
    private static int tripId ;
    public static final String TRIP_ID = "Trip Id";
    public static final String TRIP_TITLE = "sending the object";
    public static final String TRIP_UNIQUE_ID = "UniqueId";
    public static final String TRIP_DATE = "Trip date";
    public static final String TRIP_TIME = "Trip time";
    public static final String TRIP_START_POINT = "Start Point";
    public static final String TRIP_END_POINT = "End Point";

    public static final String TRIP_SET_TIME = "Trip Set Time";
    public static DatabaseReference reference = null;

    ArrayList<Trip> arrayList = null ;

    public static final String DAY = "day";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String HOUR = "hour";
    public static final String MINUTE = "minute";

    SharedPreferences sh;
    SharedPreferences.Editor editor;

    FloatingActionButton btn_add;
    TextView txt_date, txt_time;
    AutoCompleteTextView txt_StartPoint, txt_endPoint;
    EditText txt_title;
    Spinner txt_repeat, txt_kind;
    Calendar calendarDate;
    Calendar theSetTime;
    String date;
    String time;
    int anHour;
    int aMinute;
    int aYear;
    int aMonth;
    int aDay;

    DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_app_data);

        sh = getSharedPreferences(TRIP_ID,MODE_PRIVATE);

        editor = sh.edit();
        intiComponent();

        getEditData();

        FirebaseDatabase data = FirebaseDatabase.getInstance();
        reference = data.getReference("Trip_Data");

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                
                Trip trip = saveData();
                if (trip == null) {

                    Toast.makeText(TripAppDataActivity.this, "Please complete the all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intentToMainActivity = new Intent(TripAppDataActivity.this, MainActivity.class);
                    TripAppDataActivity.this.startActivity(intentToMainActivity);
                    Alarm alarm = new Alarm(tripId, trip.getHour(), trip.getMinute(), trip.getDay(), trip.getMonth(), trip.getYear(), System.currentTimeMillis(), true, trip.getTitle());
                    alarm.schedule(getApplicationContext());
                    String id = reference.push().getKey();
                    Trip data_trip = trip;

                    reference.child(id).setValue(data_trip);
                    txt_title.setText("");
                    txt_StartPoint.setText("");
                    txt_endPoint.setText("");
                    txt_date.setText("");
                    txt_time.setText("");
                    txt_kind.setSelection(0);
                    txt_repeat.setSelection(0);
                }
            }
        });


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
                txt_StartPoint.setText(intent.getStringExtra(TRIP_START_POINT), false);
                txt_endPoint.setText(intent.getStringExtra(TRIP_END_POINT), false);

                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.US);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd /MM /yyyy", Locale.US);
                txt_date.setText(dateFormat.format(theSetTime.getTime()));
                txt_time.setText(timeFormat.format(theSetTime.getTime()));

            }
        }
    }

    private void showTime() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                anHour = hourOfDay;
                aMinute = minute;
                Calendar calendarTime = Calendar.getInstance();
                calendarTime.set(Calendar.MINUTE, aMinute);
                calendarTime.set(Calendar.HOUR_OF_DAY, anHour);
                anHour = calendarTime.get(Calendar.HOUR_OF_DAY);
                Toast.makeText(TripAppDataActivity.this, String.valueOf(anHour), Toast.LENGTH_SHORT).show();
                aMinute = calendarTime.get(Calendar.MINUTE);
                SimpleDateFormat format = new SimpleDateFormat("hh:mm aa", Locale.US);
                time = format.format(calendarTime.getTime());
                txt_time.setText(time);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }

    private void showDate() {
        onDateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            date = dayOfMonth + "/" + month + "/" + year;
            txt_date.setText(date);
            aYear = year;
            aMonth = month;
            aDay = dayOfMonth;
            calendarDate = Calendar.getInstance();
            calendarDate.set(year, month, dayOfMonth);

        };
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    private void intiComponent() {
        btn_add = findViewById(R.id.add);
        txt_date = findViewById(R.id.due_date);
        txt_time = findViewById(R.id.set_time);
        txt_title = findViewById(R.id.title);
        txt_StartPoint = findViewById(R.id.your_location);
        txt_endPoint = findViewById(R.id.your_destination);
        txt_repeat = findViewById(R.id.repeatation);
        txt_kind = findViewById(R.id.kind_of_trip);
        txt_StartPoint.setAdapter(new PlaceAutocomplete(this, android.R.layout.simple_list_item_1));

        txt_endPoint.setAdapter(new PlaceAutocomplete(this, android.R.layout.simple_list_item_1));
    }

    private Trip saveData() {
        Trip data = new Trip();
        if (theSetTime == null) {
            theSetTime = Calendar.getInstance();
            theSetTime.set(Calendar.HOUR, anHour);
            theSetTime.set(Calendar.MINUTE, aMinute);
            theSetTime.set(Calendar.DAY_OF_MONTH, aDay);
            theSetTime.set(Calendar.MONTH, aMonth);
            theSetTime.set(Calendar.YEAR, aYear);
        }


        if (txt_date.getText().toString().isEmpty()
                || txt_title.getText().toString().isEmpty()
                || txt_time.getText().toString().isEmpty()
                || txt_StartPoint.getText().toString().isEmpty()
                || txt_endPoint.getText().toString().isEmpty()) {
            return null;
        } else {
            
            tripId =sh.getInt(TRIP_ID,0) + 1 ;
            editor.putInt(TRIP_ID, tripId);
            editor.commit();
            data.setMinute(aMinute);
            data.setHour(anHour);
            data.setYear(aYear);
            data.setMonth(aMonth);
            data.setDay(aDay);
            data.setTitle(txt_title.getText().toString());
            data.setDateText(txt_date.getText().toString());
            data.setTimeText(txt_time.getText().toString());
            data.setRound("Round".equals(String.valueOf(txt_repeat.getSelectedItem())));
            data.setStartPoint(String.valueOf(txt_StartPoint.getText()));
            data.setEndPoint(String.valueOf(txt_endPoint.getText()));
            data.setLatitude(29.924526);
            data.setLongitude(31.205753);
            data.setTripId(tripId);
            return data;
        }
    }

}