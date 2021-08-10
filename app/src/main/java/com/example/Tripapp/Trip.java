package com.example.Tripapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class Trip implements Parcelable {
    private String title, startPoint, endPoint, status, repetition;
    private Date date;
    private Date time;
    private ArrayList<String> notes;
    private Boolean isRound;


    private int colorId;

    public Trip(String title) {
        this.title = title;
    }

    public Trip(String title, String startPoint, String endPoint, String status, Date date, int colorId) {
        this.title = title;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.status = status;
        this.date = date;
        this.colorId = colorId;
    }

    public Trip(String title, String startPoint, String endPoint, Date date, ArrayList<String> notes, Boolean isRound) {
        this.title = title;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.date = date;
        this.notes = notes;
        this.isRound = isRound;
    }

    public Trip(String title, String startPoint, String endPoint, Date date, Date time) {
        this.title = title;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.date = date;
        this.time = time;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
