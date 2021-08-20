package com.example.Tripapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Tripapp.Data.Alarm;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.example.Tripapp.MainActivity.databaseRefUsers;

public class TripAppDataActivity extends AppCompatActivity {
    private static int alarmId = -1;
    public static final String TRIP_ID = "Trip Id";
    public static final String ALARM_ID = "Alarm id";
    public static final String TRIP_TITLE = "sending the object";
    public static final String TRIP_DATE = "Trip date";
    public static final String TRIP_TIME = "Trip time";
    public static final String TRIP_START_POINT = "Start Point";
    public static final String TRIP_END_POINT = "000000000";
    public static final String NOTES = "Notes";
    public static final String IS_ROUND = "End Point";

//    public static DatabaseReference reference = null;

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
    private EditText txt_title;
    private Spinner txt_repeat;
    private RadioButton radioButtonRound, radioButtonOneWay;
    private Calendar calendarDate;
    private Calendar theSetTime;
    private String date;
    private String time;
    private int anHour;
    private int aMinute;
    private int aYear;
    private int aMonth;
    private int aDay;
    private boolean isRound;

    String id = null;

    DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_app_data);

        sh = getSharedPreferences(TRIP_ID, MODE_PRIVATE);
        isRound = false;

        editor = sh.edit();
        intiComponent();

        getEditData();

        FirebaseDatabase data = FirebaseDatabase.getInstance();
//        reference = data.getReference("Trip_Data");

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Trip trip = saveData();
                if (trip == null) {

                    Toast.makeText(TripAppDataActivity.this, "Please complete the all fields", Toast.LENGTH_SHORT).show();
                } else if (theSetTime.getTimeInMillis() < System.currentTimeMillis()) {
                    DateFormat dateFormat = new SimpleDateFormat("hh:mm aa yy-MM-dd", Locale.US);
                    String dateCheck;
                    dateCheck = dateFormat.format(theSetTime.getTime());
                    Toast.makeText(getApplicationContext(), "Wrong Date... this time(" + dateCheck + ") is in past", Toast.LENGTH_LONG).show();
                } else {
                    Intent intentToMainActivity = new Intent(TripAppDataActivity.this, MainActivity.class);
                    TripAppDataActivity.this.startActivity(intentToMainActivity);
                    if (id == null) {
                        id = MainActivity.databaseRefUpcoming.push().getKey();
                    }
                    trip.setTripId(id);
                    Alarm alarm = new Alarm(id, alarmId, trip.getHour(), trip.getMinute(), trip.getDay(), trip.getMonth(), trip.getYear(), System.currentTimeMillis(), true, trip.getTitle());
                    alarm.schedule(getApplicationContext());
                    alarm.acceptAlarm(getApplicationContext(),theSetTime);
                    databaseRefUsers.child(MainActivity.userId).child(MainActivity.upcomingId).child(id).setValue(trip);

                    txt_title.setText("");
                    txt_StartPoint.setText("");
                    txt_endPoint.setText("");
                    txt_date.setText("");
                    txt_time.setText("");
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
            String s = intent.getStringExtra(TripAppDataActivity.TRIP_ID);

            if (s != null) {
                txt_title.setText(intent.getStringExtra(TRIP_TITLE));
                id = intent.getStringExtra(TripAppDataActivity.TRIP_ID);
                alarmId = intent.getIntExtra(TripAppDataActivity.ALARM_ID, 0);
                txt_date.setText(intent.getStringExtra(TripAppDataActivity.TRIP_DATE));
                txt_time.setText(intent.getStringExtra(TripAppDataActivity.TRIP_TIME));
                txt_StartPoint.setText(intent.getStringExtra(TripAppDataActivity.TRIP_START_POINT));
                txt_endPoint.setText(intent.getStringExtra(TripAppDataActivity.TRIP_END_POINT));
                intent.getSerializableExtra(TripAppDataActivity.NOTES);
                if (intent.getBooleanExtra(TripAppDataActivity.IS_ROUND, false)) {
                    radioButtonRound.setSelected(true);
                } else {
                    radioButtonOneWay.setSelected(true);
                }
                /////////////////////////////////////////////////////////////////////////////
                aMonth = intent.getIntExtra(TripAppDataActivity.MONTH, 0);
                aDay = intent.getIntExtra(TripAppDataActivity.DAY, 0);
                aYear = intent.getIntExtra(TripAppDataActivity.YEAR, 0);
                anHour = intent.getIntExtra(TripAppDataActivity.HOUR, 0);
                aMinute = intent.getIntExtra(TripAppDataActivity.MINUTE, 0);


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
//            month = month + 1;
            date = dayOfMonth + "/" + (month + 1) + "/" + year;
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

    public void onRBRoundClicked(View view) {
        isRound = true;
    }

    public void onRBOneWayClicked(View view) {
        isRound = false;
    }


    private void intiComponent() {
        btn_add = findViewById(R.id.add);
        txt_date = findViewById(R.id.due_date);
        txt_time = findViewById(R.id.set_time);
        txt_title = findViewById(R.id.title);
        txt_StartPoint = findViewById(R.id.your_location);
        txt_endPoint = findViewById(R.id.your_destination);
        txt_repeat = findViewById(R.id.repetition);
        txt_StartPoint.setAdapter(new PlaceAutocomplete(this, android.R.layout.simple_list_item_1));
        radioButtonRound = findViewById(R.id.radio_Round);
        radioButtonOneWay = findViewById(R.id.radio_one_way);

        txt_endPoint.setAdapter(new PlaceAutocomplete(this, android.R.layout.simple_list_item_1));
    }

    private Trip saveData() {
        Trip data = new Trip();
        theSetTime = Calendar.getInstance();
        theSetTime.set(Calendar.HOUR_OF_DAY, anHour);
        theSetTime.set(Calendar.MINUTE, aMinute);
        theSetTime.set(Calendar.DAY_OF_MONTH, aDay);
        theSetTime.set(Calendar.MONTH, aMonth);
        theSetTime.set(Calendar.YEAR, aYear);
        theSetTime.set(Calendar.SECOND, 0);
        theSetTime.set(Calendar.MILLISECOND, 0);


        if (txt_date.getText().toString().isEmpty()
                || txt_title.getText().toString().isEmpty()
                || txt_time.getText().toString().isEmpty()
                || txt_StartPoint.getText().toString().isEmpty()
                || txt_endPoint.getText().toString().isEmpty()) {
            return null;
        } else {
            if (alarmId < 0) {
                alarmId = sh.getInt(TRIP_ID, 0) + 1;
                editor.putInt(TRIP_ID, alarmId);
                editor.commit();
            }
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
            data.setStartLatitude(29.924526);
            data.setStartLongitude(31.205753);
            data.setAlarmId(alarmId);
            data.setRound(isRound);
            data.setTripKind("created");
            data.setNotes(null);
            return data;
        }
    }


}