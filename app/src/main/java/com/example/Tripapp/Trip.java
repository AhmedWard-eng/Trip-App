package com.example.Tripapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Trip{
    private String title, startPoint, endPoint, status, repetition;
    private ArrayList<String> notes;
    private Boolean isRound;
    private Calendar theSetTime;
    private String dateText;
    private String timeText;
    private Date date;
    private int colorId;



    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }


    public Date getDate() {
        return date;
    }

    public Trip(String title, String startPoint, String endPoint, String status, Date date, int colorId) {
        this.title = title;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.status = status;
        this.date = date;
        this.colorId = colorId;
    }

    public Trip(String title, String startPoint, String endPoint, String date, ArrayList<String> notes, Boolean isRound) {
        this.title = title;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.dateText = date;
        this.notes = notes;
        this.isRound = isRound;
    }




    public Calendar getTheSetTime() {
        return theSetTime;
    }

    public void setTheSetTime(Calendar theSetTime) {
        this.theSetTime = theSetTime;
    }

    public Trip(String title, String startPoint, String endPoint, String date, String time, Calendar theSetTime) {
        this.title = title;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.dateText = date;
        this.timeText = time;
        this.theSetTime = theSetTime;
    }

    Trip() {}

    public String getRepetition() {
        return repetition;
    }

    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
    public ArrayList<String> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }
    public Boolean getRound() {
        return isRound;
    }
    public void setRound(Boolean round) {
        isRound = round;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

}
