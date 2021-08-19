package com.example.Tripapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Trip {


    private int tripId;
    private String Id ;
    private String title;
    private String startPoint, endPoint , trip_kind , repeatation;
    double latitude, longitude;
    private String[] notes;
    private Boolean isRound;
    private String dateText;
    private String timeText;
    private int colorId;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;


    public Trip(String title, String startPoint, String endPoint, double latitude, double longitude, String dateText, String timeText, int day, int month, int year, int hour, int minute) {
        this.title = title;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateText = dateText;
        this.timeText = timeText;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public Trip() {
    }



    public Trip(String title, String startPoint, String endPoint, double latitude, double longitude, String dateText, String timeText, int day, int month, int year, int hour, int minute, String[] notes) {

    public Trip (String Id, String title, String startPoint, String endPoint
            , String dateText, String timeText , String trip_kind , String repeatation
            ,int hour ,int minute , int day ,int month , int year) {
        this.Id = Id;

        this.title = title;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.dateText = dateText;
        this.timeText = timeText;
        this.trip_kind = trip_kind;
        this.repeatation = repeatation;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.notes = notes;
    }

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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getNotes() {
        return notes;
    }

    public void setNotes(String[] notes) {
        this.notes = notes;
    }

    public Boolean getRound() {
        return isRound;
    }

    public void setRound(Boolean round) {
        isRound = round;
    }


    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


}




