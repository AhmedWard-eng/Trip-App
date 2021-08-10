package com.example.Tripapp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class TripAppDataActivity extends AppCompatActivity {
    FloatingActionButton btn_add;
    Button btn_notes ;
    TextView txt_notes,view_notes,txt_date,txt_time ;
    EditText txt_title ;
    SearchView txt_your_location,txt_your_distenation;
    Spinner txt_repeat,txt_kind;

    int i=0 ;
    String [] notes = new String[20];
    ImageView clock,date;
    DatePickerDialog.OnDateSetListener Date;
    int year,month,day,hours,mins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_app_data);

        inticompontent();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveData();
            }
        });

        btn_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes[i]=txt_notes.getText().toString();
                view_notes.append(txt_notes.getText()+"\n");
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

        Calendar calendartime = Calendar.getInstance();
        hours = calendartime.get(Calendar.HOUR_OF_DAY);
        mins = calendartime.get(Calendar.MINUTE);

        txt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime();
            }
        });
    }

    private void showTime(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,R.style.Theme_AppCompat_DayNight_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                c.set(Calendar.MINUTE,minute);
                c.setTimeZone(TimeZone.getDefault());
                SimpleDateFormat format = new SimpleDateFormat("kk:mm aa");
                String time = format.format(c.getTime());
                txt_time.setText(time);
            }
        },hours,mins,false);
        timePickerDialog.show();
    }
    private void showDate(){
        Date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                txt_date.setText(date);
            }
        };
        DatePickerDialog datePickerDialog=new DatePickerDialog(this,Date,year,month,day);
        datePickerDialog.show();
    }

    private void inticompontent() {
        btn_add=findViewById(R.id.add);
        btn_notes =findViewById(R.id.Notes);
        txt_date=findViewById(R.id.due_date);
        txt_notes=findViewById(R.id.txt_notes);
        txt_time=findViewById(R.id.set_time);
        txt_title=findViewById(R.id.title);
        txt_your_location=findViewById(R.id.your_location);
        txt_your_distenation=findViewById(R.id.your_distenation);
        txt_repeat=findViewById(R.id.repeatation);
        txt_kind=findViewById(R.id.kind_of_trip);
        view_notes= findViewById(R.id.notes);
        clock=findViewById(R.id.clock);
        date=findViewById(R.id.date);

    }
//    private void saveData(){
//        data_activity_trip data = new data_activity_trip();
//        data.setTitle(txt_title.getText().toString());
//        data.setDate(txt_date.getText().toString());
//        data.setTime(String.valueOf(txt_time.getText()));
//        data.setKind_trip(String.valueOf(txt_repeat.getSelectedItem()));
//        data.setRepeat(String.valueOf(txt_kind.getSelectedItem()));
//        data.setYour_location(String.valueOf(txt_your_location.getTextAlignment()));
//        data.setYour_distenation(String.valueOf(txt_your_distenation.getTextAlignment()));
//        data.setNotes(notes);
//
//    }

}